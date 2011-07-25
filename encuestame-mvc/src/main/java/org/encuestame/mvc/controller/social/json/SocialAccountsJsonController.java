/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.mvc.controller.social.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.security.SocialAccountBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;


/**
 * Social Account Json Service.
 * @author Picado, Juan juanATencuestame.org
 * @since  Feb 19, 2011 13:20:58 AM
 */
@Controller
public class SocialAccountsJsonController extends AbstractJsonController {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Twitter Instance.
     */
    private Twitter twitter = null;

    /**
     * Request Token.
     */
    private static RequestToken requestToken = null;

    /**
     * Change state of social account.
     * @param type
     * @param socialAccountId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/social/twitter/account/{type}.json", method = RequestMethod.GET)
    public ModelMap actionTwitterAccount(
            @PathVariable String type,
            @RequestParam(value = "socialAccountId", required = true) Long socialAccountId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
//         try {
//           //getSecurityService().changeStateSocialAccount(socialAccountId, getUserPrincipalUsername(), type);
//        } catch (IllegalSocialActionException e) {
//            setError(e.getMessage(), response);
//        } catch (EnMeNoResultsFoundException e) {
//            setError(e.getMessage(), response);
//        }
        return returnData();
    }

    /**
     * Return Social Valid Accounts.
     * @param request
     * @param response
     * @param provider
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/social/confirmed-accounts.json", method = RequestMethod.GET)
    public ModelMap get(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "provider", required = false) String provider)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
           final List<SocialAccountBean> accounts = getSecurityService()
                   .getUserLoggedVerifiedSocialAccounts(SocialProvider.getProvider(provider));
             setItemReadStoreResponse("socialAccounts", "id", accounts);
             log.debug("Twitter Accounts Loaded");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * Return Social Valid Accounts.
     * @param request
     * @param response
     * @param provider
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/social/accounts.json", method = RequestMethod.GET)
    public ModelMap getSocialAccountByType(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "provider", required = false) String provider)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
            final List<SocialAccountBean> accounts = getSecurityService()
            .getUserLoggedVerifiedSocialAccounts(SocialProvider.getProvider(provider));
            setItemReadStoreResponse("socialAccounts", "id", accounts);
             log.debug("Twitter Accounts Loaded");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * Return list of enabled providers.
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/social/providers.json", method = RequestMethod.GET)
    public ModelMap getProvidersEnabled(
            HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
             final HashMap<String, Object> jsonResponse = new HashMap<String, Object>();
             final List<SocialProvider> providers = new ArrayList<SocialProvider>();
                 providers.add(SocialProvider.TWITTER);
                 providers.add(SocialProvider.GOOGLE);
                 providers.add(SocialProvider.LINKEDIN);
                 providers.add(SocialProvider.IDENTICA);
                 providers.add(SocialProvider.FACEBOOK);
             jsonResponse.put("provider", providers);
             setItemResponse(jsonResponse);
             log.debug("Social providers enabled "+providers.size());
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            setError(e.getMessage(), response);
        }
        return returnData();
    }
}
