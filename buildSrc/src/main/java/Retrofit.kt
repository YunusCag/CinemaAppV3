object Retrofit {
    private const val version = "2.9.0"
    private const val codegen = "1.14.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:$version"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$version"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:$codegen"
    const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:$codegen"

    private const val okHttpVersion = "5.0.0-alpha.2"
    const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
}