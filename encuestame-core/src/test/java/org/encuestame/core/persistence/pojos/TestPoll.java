/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.persistence.pojos;

import java.util.Date;

import org.encuestame.core.persistence.pojo.Poll;
import org.encuestame.core.persistence.pojo.PollResult;
import org.encuestame.core.service.config.AbstractBase;
import org.junit.Test;

/**
 * Test Poll.
 * @author Morales,Diana Paola paola@encuestame.org
 * @since  March 15, 2009
 * @version $Id: $
 */
public class TestPoll extends AbstractBase {

    /**
     * Test Poll.
     */
    @Test
     public void testPoll(){
        final Poll poll = new Poll();
        poll.setCreatedAt(new Date());
        poll.setQuestion(createQuestion("Do you eat pizza", "yesNo"));
        poll.setPollHash("HASH");
        poll.setPollOwner(createUser());
        poll.setPollCompleted(true);
        getiPoll().saveOrUpdate(poll);
        assertNotNull(poll.getPollId());

         }

    /**
     * Test Result Poll.
     */

    @Test
    public void testPollResult(){
        final PollResult pollResult = new PollResult();
        pollResult.setAnswer(createQuestionAnswer("Si", createQuestion("Do you like eat vigoron?","yesNo"), "ABC"));
        pollResult.setIpaddress("127.0.0.1");
        pollResult.setPoll(createPoll());
        pollResult.setVotationDate(new Date());
        getiPoll().saveOrUpdate(pollResult);
        assertNotNull(pollResult.getPollResultId());

    }

}