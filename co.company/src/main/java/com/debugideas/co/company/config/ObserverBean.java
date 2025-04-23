package com.debugideas.co.company.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.annotation.*;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.LogRecordProcessor;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.SdkLoggerProviderBuilder;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration(proxyBeanMethods = false)
public class ObserverBean {

    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }

    /**
     * Crea un SdkLoggerProvider con OpenTelemetry.
     * Asigna el nombre del servicio y agrega los LogRecordProcessors configurados.
     * Permite enviar logs con trazabilidad (traceId, spanId) a herramientas como Grafana o Tempo.
     */
    @Bean
    public SdkLoggerProvider sdkLoggerProvider(Environment environment, ObjectProvider<LogRecordProcessor> processors) {
        String applicationName = environment.getProperty("spring.application.name", "application");

        Resource springResource = Resource.create(
                Attributes.of(AttributeKey.stringKey("service.name"), applicationName)
        );

        SdkLoggerProviderBuilder builder = SdkLoggerProvider.builder()
                .setResource(Resource.getDefault().merge(springResource));

        processors.orderedStream().forEach(builder::addLogRecordProcessor);

        return builder.build();
    }


    /**
     * Configura la instancia principal de OpenTelemetry utilizada para trazas, métricas y logs.
     */
    @Bean
    public OpenTelemetry openTelemetry(SdkLoggerProvider provider,
                                       SdkTracerProvider tracerProvider,
                                       ContextPropagators contextPropagators) {
        return OpenTelemetrySdk
                .builder()
                .setLoggerProvider(provider)           // Exportador de logs configurado (si se usa)
                .setTracerProvider(tracerProvider)     // Proveedor de trazas configurado
                .setPropagators(contextPropagators)    // Propagadores (por ejemplo, W3C TraceContext)
                .build();
    }

    /**
     * Procesador de logs que envía registros a través del protocolo OTLP usando gRPC.
     * Se conecta al collector de OpenTelemetry en localhost:4317.
     */
    @Bean
    public LogRecordProcessor logRecordProcessor() {
        OtlpGrpcLogRecordExporter exporter = OtlpGrpcLogRecordExporter.builder()
                .setEndpoint("http://localhost:4317")
                .build();

        return BatchLogRecordProcessor.builder(exporter).build();
    }

    /**
     * Permite anotar métodos con @NewSpan para crear trazas automáticamente.
     * Requiere una implementación de MethodInvocationProcessor.
     */
    @Bean
    public SpanAspect spanAspect(MethodInvocationProcessor processor) {
        return new SpanAspect(processor);
    }

    /**
     * Procesador que interpreta las anotaciones @NewSpan en métodos.
     * Utiliza el tracer y el contenedor de beans para aplicar el aspecto.
     */
    @Bean
    public MethodInvocationProcessor methodInvocationProcessor(NewSpanParser spanParser,
                                                               Tracer tracer,
                                                               BeanFactory beanFactory){
        return new ImperativeMethodInvocationProcessor(
                spanParser, tracer, beanFactory::getBean, beanFactory::getBean
        );
    }

    /**
     * Define cómo interpretar la anotación @NewSpan.
     * El parser extrae los nombres de spans y otras configuraciones desde las anotaciones.
     */
    @Bean
    public NewSpanParser spanParser(){
        return new DefaultNewSpanParser();
    }

    /**
     * Permite utilizar la anotación @Timed para medir la duración de métodos
     * y exportar esos tiempos como métricas.
     */
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry){
        return new TimedAspect(registry);
    }

}
