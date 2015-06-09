package ro.sapientia2015.story.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia2015.story.model.Story;
import ro.sapientia2015.story.model.StoryPoint;

/**
 * @author Kiss Tibor
 */
public interface StoryPointRepository extends JpaRepository<StoryPoint, Long> {
}
