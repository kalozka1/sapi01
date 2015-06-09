package ro.sapientia2015.story;

import org.springframework.test.util.ReflectionTestUtils;

import ro.sapientia2015.story.dto.StoryDTO;
import ro.sapientia2015.story.dto.StoryPointDTO;
import ro.sapientia2015.story.model.Story;
import ro.sapientia2015.story.model.StoryPoint;

public class StoryPointTestUtil {

    public static final Long ID = 1L;
    public static final String DESCRIPTION = "description";
    public static final String DESCRIPTION_UPDATED = "updatedDescription";
    public static final String TITLE = "title";
    public static final String TITLE_UPDATED = "updatedTitle";
    
    public static final Long STORYPOINT = (long) 15;

    private static final String CHARACTER = "a";

    public static StoryPointDTO createFormObject(Long id, StoryDTO storyDTO, Long storyPoint) {
    	StoryPointDTO dto = new StoryPointDTO();

        dto.setId(id);
        dto.setStory(storyDTO);
        dto.setStoryPoint(storyPoint);

        return dto;
    }

    public static StoryPoint createModel(Long id, Story story, Long storyPoint) {
        StoryPoint model = new StoryPoint(story, storyPoint);

        ReflectionTestUtils.setField(model, "id", id);

        return model;
    }

    public static String createRedirectViewPath(String path) {
        StringBuilder redirectViewPath = new StringBuilder();
        redirectViewPath.append("redirect:");
        redirectViewPath.append(path);
        return redirectViewPath.toString();
    }

    public static String createStringWithLength(int length) {
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < length; index++) {
            builder.append(CHARACTER);
        }

        return builder.toString();
    }
}
