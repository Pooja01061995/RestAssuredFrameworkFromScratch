package Endpoints;
//main - https://petstore.swagger.io/#
//create user - https://petstore.swagger.io/#/user/createUser
//get user - https://petstore.swagger.io/#/user/getUserByName
//update user - https://petstore.swagger.io/#/user/updateUser
//delete user - https://petstore.swagger.io/#/user/deleteUser
public class Routes {
    public static String base_url = "https://petstore.swagger.io/v2";
// User model
    public static String postuser_url = base_url + "/user";
    public static String getuser_url = base_url+"/user/{user_name}";
    public static String updateuser_url = base_url+"/user/{user_name}";
    public static String deleteuser_url = base_url+"/user/{user_name}";

   //store model
   public static String postorder_url = base_url+ "/store/order";
    public static String getorder_url    = base_url + "/store/order/{order_id}";
    public static String deleteorder_url = base_url + "/store/order/{order_id}";

    // pet model
    public static String postpet_url   = base_url + "/pet";
    public static String getpet_url    = base_url + "/pet/{pet_id}";
    public static String updatepet_url = base_url + "/pet";
    public static String deletepet_url = base_url + "/pet/{pet_id}";


}
