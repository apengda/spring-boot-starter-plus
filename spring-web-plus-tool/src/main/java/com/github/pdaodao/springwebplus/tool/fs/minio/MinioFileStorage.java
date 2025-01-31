package com.github.pdaodao.springwebplus.tool.fs.minio;

import com.github.pdaodao.springwebplus.tool.fs.FileStorage;
import com.github.pdaodao.springwebplus.tool.fs.InputStreamWrap;
import com.github.pdaodao.springwebplus.tool.util.FilePathUtil;
import com.github.pdaodao.springwebplus.tool.util.Preconditions;
import io.minio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MinioFileStorage implements FileStorage {
    private static final Logger logger = LoggerFactory.getLogger(MinioFileStorage.class);
    private final MinioConfig config;
    private transient MinioClient client;

    public MinioFileStorage(final MinioConfig config) {
        this.config = config;
        Preconditions.checkNotBlank(config.getEndpoint(), "minio endpoint config is blank.");
        Preconditions.checkNotBlank(config.getAccessKeyId(), "minio access key is blank.");
        Preconditions.checkNotBlank(config.getAccessKeySecret(), "minio accessKeySecret is blank.");
    }

    @Override
    public void init() throws Exception {
        client = MinioClient.builder().endpoint(config.getEndpoint())
                .credentials(config.getAccessKeyId(), config.getAccessKeySecret()).build();
        boolean found = client.bucketExists(BucketExistsArgs.builder().bucket(config.getBucketName()).build());
        if (!found) {
            client.makeBucket(MakeBucketArgs.builder().bucket(config.getBucketName()).build());
        }
        logger.info("connect to minio success. {}", config.getEndpoint());
    }

    @Override
    public Boolean exist(String fullPath) {
        try {
            final String path = pathAddRoot(config.getRootPath(), fullPath);
            client.statObject(StatObjectArgs.builder().bucket(config.getBucketName())
                    .object(path).build());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String upload(final String basePath, final Long fileSize, final String fileName, final InputStream inputStream) throws IOException {
        final String fullPath = concatPathForSave(config.getRootPath(), basePath, fileName);
        String contentType = new MimetypesFileTypeMap().getContentType(new File(fullPath));
        PutObjectArgs build = PutObjectArgs.builder().bucket(config.getBucketName())
                .object(fullPath)
                .stream(inputStream, fileSize, -1)
                .contentType(contentType)
                .build();
        try {
            client.putObject(build);
        } catch (Exception e) {
            throw new IOException(e);
        }
        return FilePathUtil.dropRootPath(fullPath, config.getRootPath());
    }

    @Override
    public InputStreamWrap download(String fullPath) throws IOException {
        try {
            final String path = pathAddRoot(config.getRootPath(), fullPath);
            final GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(config.getBucketName())
                    .object(path)
                    .build();
            return InputStreamWrap.of(client.getObject(getObjectArgs));
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public boolean delete(String fullPath) throws IOException, UnsupportedOperationException {
        try {
            final String path = pathAddRoot(config.getRootPath(), fullPath);
            client.removeObject(RemoveObjectArgs.builder()
                    .bucket(config.getBucketName())
                    .object(path).build());
            return true;
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public String processPathForSave(String fullPath) {
        return fullPath.startsWith("/")
                ? fullPath.substring(1) : fullPath;
    }
}
