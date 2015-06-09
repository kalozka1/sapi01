package ro.sapientia2015.story.model;

import org.junit.Test;

import ro.sapientia2015.story.model.Story;
import static junit.framework.Assert.*;

/**
 * @author Kiss Tibor
 */
public class StoryPointTest {
	private Long storyPoint = (long) 15;
	private String title = "title";
	private String description = "description";
    
    @Test
    public void buildWithNullInformation() {
        StoryPoint built = new StoryPoint();

        assertNull(built.getId());
        assertNull(built.getStory());
        assertNull(built.getStoryPoint());
    }

    @Test
    public void buildWithMissingInformation() {
        StoryPoint built = new StoryPoint();
        
        built.setStoryPoint(storyPoint);
        
        assertNull(built.getId());
        assertNull(built.getStory());
        assertEquals(storyPoint, built.getStoryPoint());
    }

    @Test
    public void buildWithAllInformation() {
        StoryPoint built = new StoryPoint();

        Story story = Story.getBuilder(title).description(description).build();
        built.setStory(story);
        built.setStoryPoint(storyPoint);
        
        assertNull(built.getId());
        assertEquals(story, built.getStory());
        assertEquals(storyPoint, built.getStoryPoint());
    }

    private void pause(long timeInMillis) {
        try {
            Thread.currentThread().sleep(timeInMillis);
        }
        catch (InterruptedException e) {
            //Do Nothing
        }
    }
}
