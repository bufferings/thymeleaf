/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2014, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf.processor.template;

import org.thymeleaf.context.ITemplateProcessingContext;
import org.thymeleaf.engine.ITemplateStructureHandler;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.model.ITemplateEnd;
import org.thymeleaf.model.ITemplateStart;
import org.thymeleaf.processor.AbstractProcessor;
import org.thymeleaf.templatemode.TemplateMode;

/**
 *
 * @author Daniel Fern&aacute;ndez
 *
 * @since 3.0.0
 *
 */
public abstract class AbstractTemplateProcessor
        extends AbstractProcessor implements ITemplateProcessor {



    public AbstractTemplateProcessor(final TemplateMode templateMode, final int precedence) {
        super(templateMode, precedence);
    }




    public final void processTemplateStart(
            final ITemplateProcessingContext processingContext,
            final ITemplateStart templateStart,
            final ITemplateStructureHandler structureHandler) {

        try {

            doProcessTemplateStart(processingContext, templateStart, structureHandler);

        } catch (final TemplateProcessingException e) {
            if (templateStart.hasLocation()) {
                if (!e.hasTemplateName()) {
                    e.setTemplateName(templateStart.getTemplateName());
                }
                if (!e.hasLineAndCol()) {
                    e.setLineAndCol(templateStart.getLine(), templateStart.getCol());
                }
            }
            throw e;
        } catch (final Exception e) {
            throw new TemplateProcessingException(
                    "Error during execution of processor '" + this.getClass().getName() + "'",
                    templateStart.getTemplateName(), templateStart.getLine(), templateStart.getCol(), e);
        }

    }


    public final void processTemplateEnd(
            final ITemplateProcessingContext processingContext,
            final ITemplateEnd templateEnd,
            final ITemplateStructureHandler structureHandler) {

        try {

            doProcessTemplateEnd(
                    processingContext, templateEnd, structureHandler);

        } catch (final TemplateProcessingException e) {
            if (templateEnd.hasLocation()) {
                if (!e.hasTemplateName()) {
                    e.setTemplateName(templateEnd.getTemplateName());
                }
                if (!e.hasLineAndCol()) {
                    e.setLineAndCol(templateEnd.getLine(), templateEnd.getCol());
                }
            }
            throw e;
        } catch (final Exception e) {
            throw new TemplateProcessingException(
                    "Error during execution of processor '" + this.getClass().getName() + "'",
                    templateEnd.getTemplateName(), templateEnd.getLine(), templateEnd.getCol(), e);
        }

    }





    public abstract void doProcessTemplateStart(
            final ITemplateProcessingContext processingContext,
            final ITemplateStart templateStart,
            final ITemplateStructureHandler structureHandler);


    public abstract void doProcessTemplateEnd(
            final ITemplateProcessingContext processingContext,
            final ITemplateEnd templateEnd,
            final ITemplateStructureHandler structureHandler);




}