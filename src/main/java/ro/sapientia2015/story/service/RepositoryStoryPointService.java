package ro.sapientia2015.story.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.sapientia2015.story.dto.StoryDTO;
import ro.sapientia2015.story.dto.StoryPointDTO;
import ro.sapientia2015.story.exception.NotFoundException;
import ro.sapientia2015.story.model.Story;
import ro.sapientia2015.story.model.StoryPoint;
import ro.sapientia2015.story.repository.StoryPointRepository;
import ro.sapientia2015.story.repository.StoryRepository;

import javax.annotation.Resource;

import java.util.List;

/**
 * @author Kiss Tibor
 */
@Service
public class RepositoryStoryPointService implements StoryPointService {

    @Resource
    private StoryPointRepository repository;

    @Transactional
    @Override
    public StoryPoint add(StoryPointDTO added) {

    	Story story = Story.getBuilder(added.getStory().getTitle()).description(added.getStory().getDescription()).build();
    	
    	StoryPoint model = new StoryPoint(story, added.getStoryPoint());

        return repository.save(model);
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public StoryPoint deleteById(Long id) throws NotFoundException {
    	StoryPoint deleted = findById(id);
        repository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<StoryPoint> findAll() {
       return repository.findAll();
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public StoryPoint findById(Long id) throws NotFoundException {
    	StoryPoint found = repository.findOne(id);
        if (found == null) {
            throw new NotFoundException("No entry found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public StoryPoint update(StoryPointDTO updated) throws NotFoundException {
    	StoryPoint model = findById(updated.getId());
    	
    	Story story = Story.getBuilder(updated.getStory().getTitle()).description(updated.getStory().getDescription()).build();
    	
        model.update(story, updated.getStoryPoint());

        return model;
    }
}
