package com.blog.kiwi.api.common

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.snippet.Attributes

internal interface ApiDocumentUtils {

    companion object {
        val documentRequest: OperationRequestPreprocessor
            get() = Preprocessors.preprocessRequest(
                Preprocessors.modifyUris()
                    .scheme("http")
                    .host("localhost")
                    .removePort(),
                Preprocessors.prettyPrint()
            )

        val documentResponse: OperationResponsePreprocessor
            get() {
                return Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
            }

        fun required(): Attributes.Attribute {
            return Attributes.key("required").value("true")
        }

        fun optional(): Attributes.Attribute {
            return Attributes.key("required").value("false")
        }

        fun defaultValue(defaultValue: Any?): Attributes.Attribute {
            return Attributes.key("defaultValue").value(defaultValue)
        }

        fun localDateTimeFormat(format: String): Attributes.Attribute {
            return Attributes.key("format").value(format)
        }
    }
}
