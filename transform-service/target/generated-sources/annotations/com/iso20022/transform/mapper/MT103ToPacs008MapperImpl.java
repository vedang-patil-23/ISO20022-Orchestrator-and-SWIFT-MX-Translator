package com.iso20022.transform.mapper;

import com.iso20022.common.model.SwiftMessageDTO;
import com.iso20022.transform.model.MXMessage;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-14T13:37:57+0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.1 (Homebrew)"
)
@Component
public class MT103ToPacs008MapperImpl implements MT103ToPacs008Mapper {

    @Override
    public MXMessage map(SwiftMessageDTO swiftMessage) {
        if ( swiftMessage == null ) {
            return null;
        }

        MXMessage mXMessage = new MXMessage();

        mXMessage.setMessageId( swiftMessage.getMessageId() );
        mXMessage.setOriginalMessage( swiftMessage.getContent() );

        mXMessage.setDocumentType( "pacs.008.001.08" );
        mXMessage.setDocument( mapToDocument(swiftMessage) );

        return mXMessage;
    }
}
