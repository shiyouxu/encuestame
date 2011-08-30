/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.service.imp.IFrontEndService;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.HashTagBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * HashTag Controller.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Apr 15, 2011
 */
@Controller
public class HashTagController extends AbstractBaseOperations{

    /** Log. **/
        private Log log = LogFactory.getLog(this.getClass());

        /** HashTag max results. **/
        private final Integer MAX_HASHTAG = 80;

        /** HashTag max results. **/
        private final Integer LIMIT_HASHTAG = 15;
    /**
     * HashTag List.
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/cloud", method = RequestMethod.GET)
    public String hashTagController(ModelMap model, HttpServletRequest request,
                  HttpServletResponse response) {
        final IFrontEndService service = getFrontService();
        final List<HashTagBean> hashTagList = service.getHashTags(MAX_HASHTAG, 0, "hashTagsCloud"); //TODO: Add to file properties number 20
        log.debug("Tag list size ---> "+ hashTagList.size());
        model.addAttribute("hashtags", hashTagList);
        return "cloud";
        }

    /**
     * HashTag detail
     * @param model
     * @param request
     * @param response
     * @param name
     * @return
     * @throws EnmeFailOperation
     */
    @RequestMapping(value = "/tag/{name}", method = RequestMethod.GET)
    public String tagController(ModelMap model , HttpServletRequest request,
                  HttpServletResponse response,
                  @PathVariable String name){
        final IFrontEndService service = getFrontService();
        log.debug("hashTag Name ---> "+name);
        name = filterValue(name);
        final String IP = getIpClient();
        log.info("IP" + IP);
        try {
             final HashTag tag = service.getHashTagItem(name);
            // Search HashTag hits.
             boolean hashTagVisite = service.checkPreviousHashTagHit(IP);
            // TODO: Check that previous hash Tag hit has been visited the same day.
             if (!hashTagVisite) {
                 service.registerHashTagHit(tag, IP);
            }
            final List<TweetPollBean> tweetPollbyTags = service.getTweetPollsbyHashTagId(tag.getHashTagId(), LIMIT_HASHTAG, "hashtag", request);
            final List<TweetPollBean> tweetPollbyRated = service.getTweetPollsbyHashTagId(tag.getHashTagId(), LIMIT_HASHTAG, "hashtagRated", request);
            if (tag == null) {
                return "pageNotFound";
            } else {
                model.addAttribute("tagName", service.getHashTagItem(name));
                model.addAttribute("tweetPolls", tweetPollbyTags);
                model.addAttribute("tweetPollrated", tweetPollbyRated);
            }
        } catch (Exception e) {
            log.error(e);
            return "pageNotFound";
        }
        return "tag/detail";
    }
}