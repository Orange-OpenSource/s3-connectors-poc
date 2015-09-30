package com.orange.clara.cloud.config;

import com.orange.cloudfoundry.connector.s3.factory.S3ContextBuilder;
import com.orange.cloudfoundry.connector.s3.factory.S3FactoryCreator;
import com.orange.cloudfoundry.connector.s3.service.info.S3ServiceInfo;
import org.jclouds.blobstore.BlobStoreContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Properties;

import static org.jclouds.Constants.PROPERTY_RELAX_HOSTNAME;
import static org.jclouds.Constants.PROPERTY_TRUST_ALL_CERTS;
import static org.jclouds.s3.reference.S3Constants.PROPERTY_S3_VIRTUAL_HOST_BUCKETS;

/**
 * Copyright (C) 2015 Orange
 * <p/>
 * This software is distributed under the terms and conditions of the 'MIT'
 * license which can be found in the file 'LICENSE' in this package distribution
 * or at 'http://opensource.org/licenses/MIT'.
 * <p/>
 * Author: Arthur Halet
 * Date: 30/09/2015
 */

@Configuration
@Profile(value = "local")
public class LocalConfig {

    @Bean
    public String bucketName() {
        return "mybucket";
    }

    @Bean
    public BlobStoreContext blobStoreContext() {
        S3ServiceInfo riakcsServiceInfo = new S3ServiceInfo("local", "s3", "localhost", 80, "access", "secret", "mybucket");
        Properties storeProviderInitProperties = new Properties();
        storeProviderInitProperties.put(PROPERTY_TRUST_ALL_CERTS, true);
        storeProviderInitProperties.put(PROPERTY_RELAX_HOSTNAME, true);
        storeProviderInitProperties.put(PROPERTY_S3_VIRTUAL_HOST_BUCKETS, false);
        S3FactoryCreator s3FactoryCreator = new S3FactoryCreator();
        S3ContextBuilder s3ContextBuilder = s3FactoryCreator.create(riakcsServiceInfo, null);
        return s3ContextBuilder.getContextBuilder().overrides(storeProviderInitProperties).buildView(BlobStoreContext.class);
    }
}