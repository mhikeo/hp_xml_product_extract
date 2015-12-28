/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers.extractors;

import com.hp.inventory.audit.parser.parsers.DocumentParser;
import com.hp.inventory.audit.parser.parsers.rules.QueriesSpec;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

import java.util.Stack;

/**
 * Extractor class for extracting the full text of a page.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class FullTextExtractor {

    private final DocumentParser parser;

    public FullTextExtractor(DocumentParser parser) {
        this.parser = parser;
    }

    /**
     * Extract the full text of the page to support advanced page search. To do this
     * we traverse the DOM tree, starting at a custom selector extracting all "text" values,
     * except for "script" and "style" tags.
     *
     * The start selector is configurable by changing the "queriesSpec.fullTextExtractStartQuery" key in
     * the rules.json file.
     */
    public String extract() {
        Document doc = parser.getDoc();

        StringBuilder s = new StringBuilder();
        final Stack<Node> stack = new Stack<>();
        Element start = doc.select(QueriesSpec.fullTextExtractStartQuery).first();
        if (start == null) {
            return null;
        }

        final Elements excluded;
        if (QueriesSpec.fullTextExtractExcludes != null) {
            excluded = start.select(QueriesSpec.fullTextExtractExcludes);
        } else {
            excluded = new Elements();
        };

        stack.push(start.parent());
        start.traverse(new NodeVisitor() {

            Element excludedTree = null;

            @Override
            public void head(Node node, int depth) {
                if (excludedTree != null) {
                    return;
                } else if (node instanceof Element && excluded.contains(node)) {
                    excludedTree = (Element) node;
                } else {
                    Node parent = stack.peek();
                    if (node instanceof TextNode && parent != null &&
                            !parent.nodeName().equals("script") &&
                            !parent.nodeName().equals("style") &&
                            !parent.nodeName().equals("noscript")) {

                        TextNode t = (TextNode) node;
                        String content = t.text().trim();
                        if (!content.equals("")) {
                            s.append(content).append(" ");
                        }
                    }
                    stack.push(node);
                }
            }

            @Override
            public void tail(Node node, int depth) {
                if (excludedTree == null) {
                    stack.pop();
                } else if (excludedTree.equals(node)) {
                    excludedTree = null;
                }
            }
        });

        return s.toString();
    }
}
