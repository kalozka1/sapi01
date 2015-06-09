package ro.sapientia2015.story.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;

import ro.sapientia2015.story.StoryPointTestUtil;
import ro.sapientia2015.story.StoryTestUtil;
import ro.sapientia2015.story.dto.StoryDTO;
import ro.sapientia2015.story.dto.StoryPointDTO;
import ro.sapientia2015.story.exception.NotFoundException;
import ro.sapientia2015.story.model.Story;
import ro.sapientia2015.story.model.StoryPoint;
import ro.sapientia2015.story.repository.StoryPointRepository;
import ro.sapientia2015.story.repository.StoryRepository;
import ro.sapientia2015.story.service.RepositoryStoryService;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.*;

/**
 * @author Kiss Tibor
 */
public class RepositoryStoryPointServiceTest {

    private RepositoryStoryPointService service;

    private StoryPointRepository repositoryMock;

    @Before
    public void setUp() {
        service = new RepositoryStoryPointService();

        repositoryMock = mock(StoryPointRepository.class);
        ReflectionTestUtils.setField(service, "repository", repositoryMock);
    }

    @Test
    public void add() {
    	StoryDTO storyDTO = StoryTestUtil.createFormObject(StoryTestUtil.ID, StoryTestUtil.DESCRIPTION, StoryTestUtil.TITLE);
    	StoryPointDTO dto = StoryPointTestUtil.createFormObject(StoryPointTestUtil.ID, storyDTO, StoryPointTestUtil.STORYPOINT);

        service.add(dto);

        ArgumentCaptor<StoryPoint> storyPointArgument = ArgumentCaptor.forClass(StoryPoint.class);
        verify(repositoryMock, times(1)).save(storyPointArgument.capture());
        verifyNoMoreInteractions(repositoryMock);

        StoryPoint model = storyPointArgument.getValue();

        assertNull(model.getId());
        assertEquals(dto.getStoryPoint(), model.getStoryPoint());
    }

    @Test
    public void deleteById() throws NotFoundException {
    	Story story = StoryTestUtil.createModel(StoryTestUtil.ID, StoryTestUtil.DESCRIPTION, StoryTestUtil.TITLE);
        StoryPoint model = StoryPointTestUtil.createModel(StoryPointTestUtil.ID, story, StoryPointTestUtil.STORYPOINT);
        when(repositoryMock.findOne(StoryPointTestUtil.ID)).thenReturn(model);

        StoryPoint actual = service.deleteById(StoryPointTestUtil.ID);

        verify(repositoryMock, times(1)).findOne(StoryPointTestUtil.ID);
        verify(repositoryMock, times(1)).delete(model);
        verifyNoMoreInteractions(repositoryMock);

        assertEquals(model, actual);
    }

    @Test(expected = NotFoundException.class)
    public void deleteByIdWhenIsNotFound() throws NotFoundException {
        when(repositoryMock.findOne(StoryPointTestUtil.ID)).thenReturn(null);

        service.deleteById(StoryPointTestUtil.ID);

        verify(repositoryMock, times(1)).findOne(StoryPointTestUtil.ID);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void findAll() {
        List<StoryPoint> models = new ArrayList<StoryPoint>();
        when(repositoryMock.findAll()).thenReturn(models);

        List<StoryPoint> actual = service.findAll();

        verify(repositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(repositoryMock);

        assertEquals(models, actual);
    }

    @Test
    public void findById() throws NotFoundException {
    	Story story = StoryTestUtil.createModel(StoryTestUtil.ID, StoryTestUtil.DESCRIPTION, StoryTestUtil.TITLE);
        StoryPoint model = StoryPointTestUtil.createModel(StoryPointTestUtil.ID, story, StoryPointTestUtil.STORYPOINT);
        when(repositoryMock.findOne(StoryPointTestUtil.ID)).thenReturn(model);

        StoryPoint actual = service.findById(StoryPointTestUtil.ID);

        verify(repositoryMock, times(1)).findOne(StoryPointTestUtil.ID);
        verifyNoMoreInteractions(repositoryMock);

        assertEquals(model, actual);
    }

    @Test(expected = NotFoundException.class)
    public void findByIdWhenIsNotFound() throws NotFoundException {
        when(repositoryMock.findOne(StoryPointTestUtil.ID)).thenReturn(null);

        service.findById(StoryPointTestUtil.ID);

        verify(repositoryMock, times(1)).findOne(StoryPointTestUtil.ID);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void update() throws NotFoundException {
    	StoryDTO storyDTO = StoryTestUtil.createFormObject(StoryTestUtil.ID, StoryTestUtil.DESCRIPTION, StoryTestUtil.TITLE);
    	StoryPointDTO dto = StoryPointTestUtil.createFormObject(StoryPointTestUtil.ID, storyDTO, StoryPointTestUtil.STORYPOINT);
    	
        Story story = StoryTestUtil.createModel(StoryTestUtil.ID, StoryTestUtil.DESCRIPTION, StoryTestUtil.TITLE);
        StoryPoint model = StoryPointTestUtil.createModel(StoryPointTestUtil.ID, story, StoryPointTestUtil.STORYPOINT);
        when(repositoryMock.findOne(dto.getId())).thenReturn(model);

        StoryPoint actual = service.update(dto);

        verify(repositoryMock, times(1)).findOne(dto.getId());
        verifyNoMoreInteractions(repositoryMock);

        assertEquals(dto.getId(), actual.getId());
        assertEquals(dto.getStoryPoint(), actual.getStoryPoint());
    }

    @Test(expected = NotFoundException.class)
    public void updateWhenIsNotFound() throws NotFoundException {
    	StoryDTO storyDTO = StoryTestUtil.createFormObject(StoryTestUtil.ID, StoryTestUtil.DESCRIPTION, StoryTestUtil.TITLE);
    	StoryPointDTO dto = StoryPointTestUtil.createFormObject(StoryPointTestUtil.ID, storyDTO, StoryPointTestUtil.STORYPOINT);
    	
        when(repositoryMock.findOne(dto.getId())).thenReturn(null);

        service.update(dto);

        verify(repositoryMock, times(1)).findOne(dto.getId());
        verifyNoMoreInteractions(repositoryMock);
    }
}
