package com.yunuscagliyan.core.util

import com.squareup.moshi.*


class MoshiParser{

    @ToJson fun toJson(writer: JsonWriter, o: Nothing?) {
        writer.nullValue()
    }

    @FromJson fun fromJson(reader: JsonReader): Nothing? {
        reader.skipValue()
        return null
    }

}

