package ro.sapientia2015.story.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ro.sapientia2015.config.ExampleApplicationContext;
import ro.sapientia2015.context.WebContextLoader;
import ro.sapientia2015.story.StoryPointTestUtil;
import ro.sapientia2015.story.StoryTestUtil;
import ro.sapientia2015.story.controller.StoryController;
import ro.sapientia2015.story.dto.StoryDTO;
import ro.sapientia2015.story.dto.StoryPointDTO;
import ro.sapientia2015.story.model.Story;

import javax.annotation.Resource;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.view;

/**
 * This test uses the annotation based application context configuration.
 * @author Kiss Tibor
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = WebContextLoader.class, classes = {ExampleApplicationContext.class})
//@ContextConfiguration(loader = WebContextLoader.class, locations = {"classpath:exampleApplicationContext.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup("storyPointData.xml")
public class ITStoryPointControllerTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webApplicationContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @ExpectedDatabase("storyPointData.xml")
    public void showAddForm() throws Exception {
        mockMvc.perform(get("/storyPoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name(StoryPointController.VIEW_ADD))
                .andExpect(forwardedUrl("/WEB-INF/jsp/storyPoint/add.jsp"))
                .andExpect(model().attribute(StoryPointController.MODEL_ATTRIBUTE, hasProperty("id", nullValue())))
                .andExpect(model().attribute(StoryPointController.MODEL_ATTRIBUTE, hasProperty("storyPoint", isEmptyOrNullString())));
    }

    @Test
    @ExpectedDatabase("storyPointData.xml")
    public void addEmpty() throws Exception {
        mockMvc.perform(post("/storyPoint/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .sessionAttr(StoryPointController.MODEL_ATTRIBUTE, new StoryPointDTO())
        )
                .andExpect(status().isOk())
                .andExpect(view().name(StoryPointController.VIEW_ADD))
                .andExpect(forwardedUrl("/WEB-INF/jsp/storyPoint/add.jsp"))
                .andExpect(model().attributeHasFieldErrors(StoryPointController.MODEL_ATTRIBUTE, "title"))
                .andExpect(model().attribute(StoryController.MODEL_ATTRIBUTE, hasProperty("id", nullValue())))
                .andExpect(model().attribute(StoryController.MODEL_ATTRIBUTE, hasProperty("storyPoint", isEmptyOrNullString())));
    }

    @Test
    @ExpectedDatabase("storyPointData.xml")
    public void addWhenStoryPointIsNegative() throws Exception {
        String storyPoint = "-1";

        mockMvc.perform(post("/story/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("storyPoint", storyPoint)
                .sessionAttr(StoryPointController.MODEL_ATTRIBUTE, new StoryPointDTO())
        )
                .andExpect(status().isOk())
                .andExpect(view().name(StoryPointController.VIEW_ADD))
                .andExpect(forwardedUrl("/WEB-INF/jsp/storyPoint/add.jsp"))
                .andExpect(model().attributeHasFieldErrors(StoryPointController.MODEL_ATTRIBUTE, "storyPoint"))
                .andExpect(model().attribute(StoryPointController.MODEL_ATTRIBUTE, hasProperty("id", nullValue())));
    }

    @Test
    @ExpectedDatabase(value="storyPointData-add-expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void add() throws Exception {
        String expectedRedirectViewPath = StoryPointTestUtil.createRedirectViewPath(StoryPointController.REQUEST_MAPPING_VIEW);

        mockMvc.perform(post("/storyPoint/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("storyPoint", "storyPoint")
                .sessionAttr(StoryPointController.MODEL_ATTRIBUTE, new StoryPointDTO())
        )
                .andExpect(status().isOk())
                .andExpect(view().name(expectedRedirectViewPath));
    }

    @Test
    @ExpectedDatabase("storyPointData.xml")
    public void findAll() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name(StoryPointController.VIEW_LIST))
                .andExpect(forwardedUrl("/WEB-INF/jsp/storyPoint/list.jsp"))
                .andExpect(model().attribute(StoryPointController.MODEL_ATTRIBUTE_LIST, hasSize(2)));
    }
}