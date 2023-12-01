/*
 * Copyright (C) 2020-present IBM Corporation
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package ix.ibm.sonar.java.visitors;

import org.apache.commons.lang.StringUtils;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.Tree;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class PrinterVisitor extends BaseTreeVisitor {

    private static final int INDENT_SPACES = 2;

    private final StringBuilder sb;
    private int indentLevel;

    public PrinterVisitor() {
        this.sb = new StringBuilder();
        this.indentLevel = 0;
    }

    public static String print(final Tree tree) {
        PrinterVisitor pv = new PrinterVisitor();
        pv.scan(tree);
        return pv.sb.toString();
    }

    private StringBuilder indent() {
        return this.sb.append(StringUtils.leftPad("", INDENT_SPACES * this.indentLevel));
    }

    @Override
    protected void scan(final List<? extends Tree> trees) {
        if (!trees.isEmpty()) {
            this.sb.deleteCharAt(this.sb.length() - 1);
            this.sb.append(" : [\n");
            super.scan(trees);
            this.indent().append("]\n");
        }
    }

    @Override
    protected void scan(@Nullable final Tree tree) {
        if (Objects.nonNull(tree) && Objects.nonNull(tree.getClass().getInterfaces()) && tree.getClass().getInterfaces().length > 0) {
            final String nodeName = tree.getClass().getInterfaces()[0].getSimpleName();
            if (Objects.nonNull(tree.firstToken())) {
                this
                  .indent()
                  .append(nodeName)
                  .append(" :")
                  .append(tree.firstToken().range().start().line())
                  .append(" | ")
                  .append(tree.firstToken().text())
                  .append("\n");
            } else {
                this.indent().append(nodeName).append("\n");
            }
        }
        this.indentLevel++;
        super.scan(tree);
        this.indentLevel--;
    }

}
