package ro.sapientia2015.story.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import ro.sapientia2015.story.model.Story;

/**
 * @author Kiss Tibor
 */
public class StoryPointDTO {

    private Long id;

    private StoryDTO story;
    
    private Long storyPoint;

    public StoryPointDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StoryDTO getStory() {
		return story;
	}

	public void setStory(StoryDTO story) {
		this.story = story;
	}

	public Long getStoryPoint() {
		return storyPoint;
	}

	public void setStoryPoint(long storyPoint) {
		this.storyPoint = storyPoint;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
