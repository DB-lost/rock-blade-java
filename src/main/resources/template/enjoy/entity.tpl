#set(withLombok = entityConfig.isWithLombok())
#set(withSwagger = entityConfig.isWithSwagger())
#set(swaggerVersion = entityConfig.getSwaggerVersion())
#set(withActiveRecord = entityConfig.isWithActiveRecord())
#set(entityClassName = table.buildEntityClassName())
package #(packageConfig.entityPackage);

#for(importClass : table.buildImports(false))
import #(importClass);
#end
#if(withActiveRecord)
import com.mybatisflex.core.activerecord.Model;
#end
#if(withSwagger && swaggerVersion.getName() == "FOX")
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
#end
#if(withSwagger && swaggerVersion.getName() == "DOC")
#end
#if(withLombok)
#if(withActiveRecord)
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
#else
import lombok.*;
import lombok.experimental.Accessors;
#end
#end

/**
 * #(table.getComment()) 实体类。
 *
 * @author #(javadocConfig.getAuthor())
 * @since #(javadocConfig.getSince())
 */
#if(withLombok)
#if(withActiveRecord)
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
#else
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
#end
#end
#(table.buildTableAnnotation())
public class #(entityClassName)#if(withActiveRecord) extends Model<#(entityClassName)>#else#(table.buildExtends(false))#(table.buildImplements())#end  {

#for(column : table.columns)
    #if(column.property != "createTime" && column.property != "createdBy" && column.property != "updateTime" && column.property != "updatedBy")
    #set(comment = javadocConfig.formatColumnComment(column.comment))
    #if(hasText(comment))
    /**
     * #(comment)
     */
    #end
    #set(annotations = column.buildAnnotations())
    #if(hasText(annotations))
    #(annotations)
    #end
    private #(column.propertySimpleType) #(column.property)#if(hasText(column.propertyDefaultValue)) = #(column.propertyDefaultValue)#end;
    #end

#end
#if(!withLombok)
    #if(withActiveRecord)
    public static #(entityClassName) create() {
        return new #(entityClassName)();
    }

    #end
    #for(column: table.columns)
    public #(column.propertySimpleType) #(column.getterMethod())() {
        return #(column.property);
    }

    #if(withActiveRecord)
    public #(entityClassName) #(column.setterMethod())(#(column.propertySimpleType) #(column.property)) {
        this.#(column.property) = #(column.property);
        return this;
    }
    #else
    public void #(column.setterMethod())(#(column.propertySimpleType) #(column.property)) {
        this.#(column.property) = #(column.property);
    }
    #end

    #end
#end}
