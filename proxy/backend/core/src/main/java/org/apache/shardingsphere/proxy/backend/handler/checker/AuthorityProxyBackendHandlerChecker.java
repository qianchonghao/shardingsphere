/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.proxy.backend.handler.checker;

import org.apache.shardingsphere.authority.checker.AuthorityChecker;
import org.apache.shardingsphere.authority.rule.AuthorityRule;
import org.apache.shardingsphere.infra.exception.core.ShardingSpherePreconditions;
import org.apache.shardingsphere.infra.exception.dialect.exception.syntax.database.UnknownDatabaseException;
import org.apache.shardingsphere.infra.metadata.ShardingSphereMetaData;
import org.apache.shardingsphere.infra.metadata.database.ShardingSphereDatabase;
import org.apache.shardingsphere.infra.metadata.user.Grantee;
import org.apache.shardingsphere.infra.session.query.QueryContext;

/**
 * Authority proxy backend handler checker.
 */
public final class AuthorityProxyBackendHandlerChecker implements ProxyBackendHandlerChecker {
    
    @Override
    public void check(final ShardingSphereMetaData metaData, final Grantee grantee, final QueryContext queryContext, final ShardingSphereDatabase database) {
        AuthorityRule authorityRule = metaData.getGlobalRuleMetaData().getSingleRule(AuthorityRule.class);
        ShardingSpherePreconditions.checkState(new AuthorityChecker(authorityRule, grantee).isAuthorized(database.getName()),
                () -> new UnknownDatabaseException(database.getName()));
    }
}
