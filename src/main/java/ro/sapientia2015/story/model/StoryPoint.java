package ro.sapientia2015.story.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * @author Kiss Tibor
 */
@Entity
@Table(name="storyPoint")
public class StoryPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "story", nullable = false)
    @OneToOne
    private Story story;

    @Column(name = "storyPoint", nullable = false)
    private Long storyPoint;
    
    @Version
    private long version;

    public StoryPoint() {

    }
    
    public StoryPoint(Story story, long storyPoint) {
    	this.story = story;
    	this.storyPoint = storyPoint;
    }

    public Long getId() {
        return id;
    }

    public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

	public Long getStoryPoint() {
		return storyPoint;
	}

	public void setStoryPoint(long storyPoint) {
		this.storyPoint = storyPoint;
	}

	public long getVersion() {
        return version;
    }
	
	public void update(Story story, long storyPoint)
	{
		this.story = story;
		this.storyPoint = storyPoint;
	}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
