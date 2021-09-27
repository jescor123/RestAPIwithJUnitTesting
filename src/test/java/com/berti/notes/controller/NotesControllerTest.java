package com.berti.notes.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.berti.notes.model.Notes;
import com.berti.notes.repository.NotesRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest
public class NotesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private   NotesRepository noteRepo;

    @Test
    public void getAllNotes() throws Exception {
    
        Notes note1 = new Notes();
        Notes note2 = new Notes();
        note1.setId(Long.valueOf(1));
        note1.setBody("This is a new note");
        note2.setId(Long.valueOf(2));
        note2.setBody("This is a second new note");
        List<Notes> notes = Arrays.asList(note1,note2);
        given(noteRepo.getAllNotes()).willReturn(notes);

        this.mockMvc.perform(get("/api/notes/"))
                .andExpect(status().isOk()).andDo(print());
              
    }


    @Test
    public void isUrlOkGetNoteById() throws Exception
    {

        this.mockMvc.perform( MockMvcRequestBuilders
                .get("/api/notes/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());

    }


    @Test
    public void testMessagePage() throws Exception {

        this.mockMvc.perform(get("/api/notes/message")).andExpect(status().isOk())
            .andExpect(content().string("Hallo da!"));

    }

}

