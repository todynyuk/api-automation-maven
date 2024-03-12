package constans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public interface UserAPIConstant {
//    static final String base_URL = "https://reqres.in/api/users";
    static final String BASE_URL = "https://gorest.co.in/public/v2";
    static final String BEARER_DATA = "Bearer 6085873cc47a8653af13c948c7e272cc18140462fd444c99cd9549711fa183ee";
    static final int HTTP_CODE_200 = 200;
    static final int HTTP_CODE_201 = 201;
    static final int HTTP_CODE_204 = 204;
    static final int HTTP_CODE_400 = 400;
    static final int HTTP_CODE_404 = 404;

    static final String HTTP_STATUS_MESSAGE_OK = "OK";
    static final String HTTP_STATUS_CREATED = "Created";
    static final String HTTP_STATUS_MESSAGE_CLIENT_DATA_ISSUE = "CLIENT DATA ISSUE";
    static final String HTTP_STATUS_MESSAGE_NOT_FOUND = "Not Found";
    static final String HTTP_STATUS_MESSAGE_NO_CONTENT = "No content";
    static final String JSON = "application/json";
}
