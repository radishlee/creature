package com.lesports.gene.vct.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/main")
public class MainController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index.jsp");
    }


    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public ModelAndView monitor() {
        ModelAndView mav = new ModelAndView("monitor.jsp");
        mav.addObject("player"," SewisePlayer.setup({\n" +
                "                                    server: \"vod\",\n" +
                "                                    type: \"m3u8\",\n" +
                "                                    autostart: \"false\",\n" +
                "                                    poster: \"http://jackzhang1204.github.io/materials/poster.png\",\n" +
                "                                    videourl: \"http://192.168.8.201/hls/test.m3u8\",\n" +
                "                                    skin: \"vodWhite\",\n" +
                "                                    title: \"Live M3U8\",\n" +
                "                                    claritybutton: \"disable\",\n" +
                "                                    lang: \"zh_CN\"\n" +
                "                                }, \"player\");");
        mav.addObject("player1"," SewisePlayer.setup({\n" +
                "                                    server: \"vod\",\n" +
                "                                    type: \"m3u8\",\n" +
                "                                    autostart: \"false\",\n" +
                "                                    poster: \"http://jackzhang1204.github.io/materials/poster.png\",\n" +
                "                                    videourl: \"http://192.168.8.201/hls/test1.m3u8\",\n" +
                "                                    skin: \"vodWhite\",\n" +
                "                                    title: \"Live M3U8\",\n" +
                "                                    claritybutton: \"disable\",\n" +
                "                                    lang: \"zh_CN\"\n" +
                "                                }, \"player1\");");
        mav.addObject("player2"," SewisePlayer.setup({\n" +
                "                                    server: \"vod\",\n" +
                "                                    type: \"m3u8\",\n" +
                "                                    autostart: \"false\",\n" +
                "                                    poster: \"http://jackzhang1204.github.io/materials/poster.png\",\n" +
                "                                    videourl: \"http://192.168.8.201/hls/test2.m3u8\",\n" +
                "                                    skin: \"vodWhite\",\n" +
                "                                    title: \"Live M3U8\",\n" +
                "                                    claritybutton: \"disable\",\n" +
                "                                    lang: \"zh_CN\"\n" +
                "                                }, \"player2\");");
        mav.addObject("player3"," SewisePlayer.setup({\n" +
                "                                    server: \"vod\",\n" +
                "                                    type: \"m3u8\",\n" +
                "                                    autostart: \"false\",\n" +
                "                                    poster: \"http://jackzhang1204.github.io/materials/poster.png\",\n" +
                "                                    videourl: \"http://192.168.8.201/hls/test3.m3u8\",\n" +
                "                                    skin: \"vodWhite\",\n" +
                "                                    title: \"Live M3U8\",\n" +
                "                                    claritybutton: \"disable\",\n" +
                "                                    lang: \"zh_CN\"\n" +
                "                                }, \"player3\");");
        return mav;
    }

    @RequestMapping(value = "/aboutus", method = RequestMethod.GET)
    public ModelAndView aboutus() {
        return new ModelAndView("aboutus.jsp");
    }
}
