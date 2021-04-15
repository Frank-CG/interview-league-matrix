# interview-league-matrix

## Running Requirements
> JDK 11

## How to build (without IDE)
> Download this project, execute following commands :
<pre><code>./gradlew bootJar
</code></pre>

## How to run
> After successfully build, you can find an executable jar in build/libs directory, execute following command to start our application:
<pre><code>java -jar matrix-1.0.0.jar
</code></pre>

## We can test it
<pre><code>curl -F 'file=@/path/matrix.csv' "localhost:8080/echo"
curl -F 'file=@/path/matrix.csv' "localhost:8080/invert"
curl -F 'file=@/path/matrix.csv' "localhost:8080/flatten"
curl -F 'file=@/path/matrix.csv' "localhost:8080/sum"
curl -F 'file=@/path/matrix.csv' "localhost:8080/multiply"
</code></pre>

## We can check API document
> Open in browser:
>> http://localhost:8080/swagger-ui.html#/
