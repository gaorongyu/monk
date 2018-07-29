package com.fngry.monk.biz.service.accounting.trf.task;

import com.fngry.monk.biz.service.accounting.common.BizEntity;
import com.fngry.monk.biz.service.accounting.common.config.FilterRule;
import com.fngry.monk.biz.service.accounting.common.filter.Filter;
import com.fngry.monk.biz.service.accounting.common.filter.FilterEnum;
import com.fngry.monk.biz.service.accounting.common.filter.ScriptFilter;
import com.fngry.monk.biz.service.accounting.vld.config.ValidConfig;
import com.fngry.monk.biz.service.accounting.vld.config.ValidatorRule;
import com.fngry.monk.biz.service.accounting.vld.validator.Validator;
import com.fngry.monk.biz.service.accounting.vld.validator.ValidatorEnum;
import com.fngry.monk.common.util.CollectionUtil;
import org.apache.spark.api.java.function.Function;

import java.lang.reflect.Constructor;
import java.util.*;

public class EntityCheckTask implements Function<BizEntity, Boolean> {

    private static final long serialVersionUID = 9217723186736413846L;

    private List<ValidConfig> validConfigList= null;

    private transient List<EntityChecker> entityCheckerList = null;

    @Override
    public Boolean call(BizEntity bizEntity) throws Exception {
        return getEntityCheckers().stream()
                .filter(e -> e.matches(bizEntity))
                .filter(e -> e.validate(bizEntity))
                .findFirst()
                .isPresent();
    }

    public Collection<EntityChecker> getEntityCheckers() {
        if (entityCheckerList != null) {
            return entityCheckerList;
        }
        // todo init from config

        entityCheckerList = new ArrayList<>();

        // for test
        EntityChecker checker = new EntityChecker();
        checker.entityMatchers = new ArrayList<>();

        FilterRule filterRule = new FilterRule();
        filterRule.setFilter("SCRIPT_FILTER");
        Map<String, Object> params = new HashMap<>();
        params.put("expression", "return target.id > 10");
        filterRule.setParams(params);
        EntityMatcher matcher = new EntityMatcher(filterRule, new ScriptFilter(params));
        checker.entityMatchers.add(matcher);

        entityCheckerList.add(checker);
        return entityCheckerList;
    }

    private static class EntityChecker {

        private Collection<EntityMatcher> entityMatchers;

        private Collection<EntityValidator> entityValidators;


        public static EntityChecker create() {
            EntityChecker checker = new EntityChecker();

            // todo matcher validator init from config
//            checker.entityMatchers = ;

            return checker;
        }

        public boolean matches(BizEntity bizEntity) {
            if (CollectionUtil.isEmpty(entityMatchers)) {
                return true;
            }
            return entityMatchers.stream().filter(e -> e.matches(bizEntity)).findFirst().isPresent();
        }

        public boolean validate(BizEntity bizEntity) {
            if (CollectionUtil.isEmpty(entityValidators)) {
                return true;
            }
            // all validate done then pass
            return entityValidators.stream().allMatch(e -> e.matches(bizEntity));
        }

    }

    private static class EntityMatcher {

        private FilterRule filterRule;

        private Filter filter;

        private EntityMatcher(FilterRule filterRule, Filter filter) {
            this.filterRule = filterRule;
            this.filter = filter;
        }

        public static EntityMatcher create(FilterRule filterRule) {
            FilterEnum filterEnum = FilterEnum.valueOf(filterRule.getFilter());
            try {
                Constructor<? extends Filter> constructor = filterEnum.getFilterClass()
                            .getDeclaredConstructor(new Class[] {Map.class});
                constructor.setAccessible(true);
                Filter filter = constructor.newInstance(filterRule.getParams());

                return new EntityMatcher(filterRule, filter);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public boolean matches(BizEntity target) {
            return this.filter.matches(filterRule.getParams(), target.getData());
        }

    }

    private static class EntityValidator {
        private ValidatorRule validatorRule;

        private Validator validator;

        private EntityValidator(ValidatorRule validatorRule, Validator validator) {
            this.validatorRule = validatorRule;
            this.validator = validator;
        }

        public static EntityValidator create(ValidatorRule validatorRule) {
            ValidatorEnum validatorEnum = ValidatorEnum.valueOf(validatorRule.getValidator());
            try {
                Constructor<? extends Validator> constructor = validatorEnum.getValidatorClass()
                        .getDeclaredConstructor(new Class[] {Map.class});
                constructor.setAccessible(true);
                Validator validator = constructor.newInstance(validatorRule.getParams());

                return new EntityValidator(validatorRule, validator);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public boolean matches(BizEntity target) {
            return this.validator.validate(validatorRule.getParams(), target.getData());
        }

    }

}
