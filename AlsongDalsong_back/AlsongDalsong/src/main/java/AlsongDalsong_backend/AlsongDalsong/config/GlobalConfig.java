package AlsongDalsong_backend.AlsongDalsong.config;

import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@Slf4j
@Getter
public class GlobalConfig {
    @Autowired
    private Environment environment;

    private String aws_accessKey;
    private String aws_secretKey;
    private String aws_region;
    private String aws_bucket;

    private String kakao_client_id;
    private String kakao_redirect_uri;

    private String jwt_secret;
    private int jwt_expiration_time;

    @PostConstruct
    public void init() {
        aws_accessKey = environment.getProperty("cloud.aws.credentials.accessKey");
        aws_secretKey = environment.getProperty("cloud.aws.credentials.secretKey");
        aws_region = environment.getProperty("cloud.aws.region.static");
        aws_bucket = environment.getProperty("cloud.aws.s3.bucket");

        kakao_client_id = environment.getProperty("kakao.client_id");
        kakao_redirect_uri = environment.getProperty("kakao.redirect_uri");

        jwt_secret = environment.getProperty("jwt.secret");
        jwt_expiration_time = Integer.parseInt(environment.getProperty("jwt.expiration_time"));
    }
}
