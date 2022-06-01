package com.codegym.controller;

import com.codegym.model.Music;
import com.codegym.model.MusicForm;
import com.codegym.service.IMusicService;
import com.codegym.service.MusicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/music")
public class MusicController {
    private final IMusicService musicService = new MusicService();
    @Value("${file-upload}")
    private String fileUpload;

    @GetMapping()
    public String index(Model model) {
        List<Music> list = musicService.findAll();
        model.addAttribute("music", list);
        return "/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("musicForm", new Music());
        return "/create";
    }

    @PostMapping("/save")
    public String save(MusicForm musicForm, Model model, RedirectAttributes attributes) {
        MultipartFile multipartFile = musicForm.getLink();
        String fileName = multipartFile.getOriginalFilename();
        String[] split = fileName.split(".");
        String tail = split[1];
        try {
            FileCopyUtils.copy(musicForm.getLink().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Music music = new Music(musicForm.getId(), musicForm.getName(), musicForm.getSinger(), musicForm.getGenre(), fileName);
        musicService.save(music);
        model.addAttribute("musicForm", musicForm);
        attributes.addFlashAttribute("mess","Add music successfully!!!");
        return "redirect:/music";
    }
}
