import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import test_utils.AllUserMethods;
import org.testng.Assert;
import org.testng.annotations.Test;
import net.minidev.json.JSONObject;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ApiGoRestUserTests {

    AllUserMethods allUserMethods = new AllUserMethods();
    Faker faker = new Faker();

    String responseBodyBuffer;

    Response response;

    JsonPath jsonPath;

    int userId;

    @Test
    public void getAllUsersTest() {
        response = allUserMethods.getAllUsers();
        int responseStatusCode = response.getStatusCode();
        Assert.assertEquals(responseStatusCode, 200);
        int expectedListUsersLength = 10;
        int actualUsersListSize = allUserMethods.getAllUsersList().size();
        Assert.assertEquals(expectedListUsersLength, actualUsersListSize, "Lists size are not equals");
    }

    @Test
    public void postCreateRandomUserTest() {
        response = allUserMethods.createRandomUser();
        Assert.assertEquals(response.getStatusCode(), 201);
        jsonPath = response.jsonPath();
        userId = jsonPath.get("id");
        responseBodyBuffer = response.getBody().asString();
    }

    @Test
    public void createUserWithoutNameTest() {
        JSONObject userInfo = new JSONObject();
        userInfo.put("gender", "female");
        userInfo.put("email", RandomStringUtils.randomAlphabetic(5) + "@gmail.com");
        userInfo.put("status", "active");
        Assert.assertEquals(AllUserMethods.createUserAndReturnStatusCode(userInfo), 422);
    }

    @Test
    public void createUserWithEmptyNameTest() {
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", "");
        userInfo.put("gender", "female");
        userInfo.put("email", RandomStringUtils.randomAlphabetic(5) + "@gmail.com");
        userInfo.put("status", "active");
        Assert.assertEquals(AllUserMethods.createUserAndReturnStatusCode(userInfo), 422);
    }

    @Test
    public void createUserWithoutGenderTest() {
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", faker.name().fullName());
        userInfo.put("email", RandomStringUtils.randomAlphabetic(5) + "@gmail.com");
        userInfo.put("status", "active");
        Assert.assertEquals(AllUserMethods.createUserAndReturnStatusCode(userInfo), 422);
    }

    @Test
    public void createUserWithoutEmailTest() {
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", faker.name().fullName());
        userInfo.put("gender", "female");
        userInfo.put("status", "active");
        Assert.assertEquals(AllUserMethods.createUserAndReturnStatusCode(userInfo), 422);
    }

    @Test
    public void createUserWithExistingEmailTest() {
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", faker.name().fullName());
        userInfo.put("gender", "female");
        userInfo.put("email", "angella.beer@yahoo.com");
        userInfo.put("status", "active");
        Assert.assertEquals(AllUserMethods.createUserAndReturnStatusCode(userInfo), 422);
    }

    @Test
    public void getUserByIdTest() {
        response = allUserMethods.createRandomUser();
        jsonPath = response.jsonPath();
        userId = jsonPath.get("id");
        response = allUserMethods.getUserById(userId);
        System.out.println("Status code: " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void putUserTest() {
        response = allUserMethods.createRandomUser();
        jsonPath = response.jsonPath();
        userId = jsonPath.get("id");
        response = allUserMethods.putMethod(userId, "Jonny", "Male");
        Assert.assertEquals(response.getStatusCode(), 200);
        jsonPath = response.jsonPath();
        String usrName = jsonPath.get("name");
        Assert.assertEquals(usrName, "Jonny");
    }

    @Test
    public void deleteUserTest() {
        response = allUserMethods.createRandomUser();
        jsonPath = response.jsonPath();
        userId = jsonPath.get("id");
        response = allUserMethods.deleteMethod(userId);
        Assert.assertEquals(response.getStatusCode(), 204);
    }
}
