Basic Otel for traces



command : 
java -javaagent:"./opentelemetry-javaagent.jar" "-Dotel.traces.exporter=logging" "-Dotel.metrics.exporter=logging" "-Dotel.logs.exporter=logging" -jar "./build/libs/app.jar"
java -javaagent:"./opentelemetry-javaagent.jar" "-Dotel.traces.exporter=logging" "-Dotel.metrics.exporter=none" "-Dotel.logs.exporter=none" -jar "./build/libs/app.jar"
