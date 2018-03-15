package com.whyuan.$6utils.log;

import java.util.HashSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>wrap solr schema info entity.
 * <p>Author: huyisen@gmail.com
 * <p>Date: 2017-04-12 13:28
 * <p>Version: 1.0
 */
public class Schema {

    private Set<FieldType> types = new HashSet<>();
    private Set<Field> fields = new HashSet<>();

    private boolean preSegment = false;
    private IK ikVersion;
    private boolean isem;

    public Schema() {
    }

    public Schema(Set<FieldType> types, Set<Field> fields) {
        this.types = types;
        this.fields = fields;
    }
    public Schema(Set<FieldType> types, Set<Field> fields,boolean preSegment) {
        this.types = types;
        this.fields = fields;
        this.preSegment=preSegment;
    }

    public enum IK {
        V1,V2
    }

    public boolean isPreSegment() {
        return preSegment;
    }

    public void setPreSegment(boolean preSegment) {
        this.preSegment = preSegment;
    }

    public Set<FieldType> getTypes() {
        return types;
    }

    public void setTypes(Set<FieldType> types) {
        this.types = types;
    }

    public Set<Field> getFields() {
        return fields;
    }

    public void setFields(Set<Field> fields) {
        this.fields = fields;
    }

    public void addField(Field field) {
        this.fields.add(field);
    }

    public void addFieldType(FieldType fieldType) {
        this.types.add(fieldType);
    }

    public List<Field> getFieldByType(String type) {
        return fields.stream()
                .filter(line -> type.equals(line.getType()))
                .collect(Collectors.toList());

    }

    public Field getFieldByName(String name) {
        List<Field> collect = fields.stream()
                .filter(line -> name.equals(line.getName()))
                .collect(Collectors.toList());
        if (collect != null && !collect.isEmpty()) {
            return collect.get(0);
        } else {
            return null;
        }
    }

    public boolean isInFieldsByName(String name) {
        List<Field> collect = fields.stream()
                .filter(line -> name.equals(line.getName()))
                .collect(Collectors.toList());
        if (collect != null && !collect.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public IK getIkVersion() {
        return ikVersion;
    }

    public void setIkVersion(IK ikVersion) {
        this.ikVersion = ikVersion;
    }

    public boolean isIsem() {
        return isem;
    }

    public void setIsem(boolean isem) {
        this.isem = isem;
    }
}
