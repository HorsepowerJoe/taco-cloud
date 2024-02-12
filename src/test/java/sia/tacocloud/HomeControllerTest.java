package sia.tacocloud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers; // static 사용으로 코드를 더 간결하게 사용 가능. ex) moc~~.status -> status()

import sia.tacocloud.tacos.controller.HomeController;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHomePage() throws Exception {
          mockMvc.perform(MockMvcRequestBuilders.get("/"))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.view().name("index.html"));
        //   .andExpect(MockMvcResultMatchers.content().string("Welcome to")); 뷰 연동으로 작동이 이상하기 때문에 주석


    }

}
