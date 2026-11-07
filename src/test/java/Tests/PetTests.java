package Tests;
import Endpoints.PetEndpoint;
import Payloads.Category;
import Payloads.Pet;
import Payloads.Tag;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;
public class PetTests {
    Faker faker;
    Pet petPayload;
    int petId;

    @BeforeClass
    public void setUpData() {
        faker = new Faker();
        petPayload = new Pet();

        petId = faker.number().numberBetween(1, 100000);
        petPayload.setId(petId);
        petPayload.setName(faker.name().firstName());
        petPayload.setPhotoUrls(List.of(faker.internet().image()));
        petPayload.setStatus("available");

        Category category = new Category();
        category.setId(faker.number().numberBetween(1, 100));
        category.setName(faker.animal().name());
        petPayload.setCategory(category);

        Tag tag = new Tag();
        tag.setId(faker.number().numberBetween(1, 100));
        tag.setName(faker.lorem().word());
        petPayload.setTags(List.of(tag));
    }

    @Test(priority = 1)
    public void testPostPet() {
        Response response = PetEndpoint.createPet(petPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2, dependsOnMethods = "testPostPet")
    public void testGetPetById() {
        Response response = PetEndpoint.getPet(petId);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3, dependsOnMethods = "testGetPetById")
    public void testUpdatePetById() {
        petPayload.setName(faker.name().firstName());
        petPayload.setStatus("sold");

        Response response = PetEndpoint.updatePet(petPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 4, dependsOnMethods = "testUpdatePetById")
    public void testDeletePetById() {
        Response response = PetEndpoint.deletePet(petId);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
