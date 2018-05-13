package com.licenta.licenta.repository;

import com.licenta.licenta.model.TitleFeatures;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleFeaturesRepository extends CrudRepository<TitleFeatures, Long> {
    Long countByisGreaterThanMeanMaxTrue();

    Long countByisTitleTrue();

//    Double countByisTitleFalse();


    @Query("select count (isTitle) from TitleFeatures  where isTitle = (:isTitle)")
    Double findIsTitle(@Param("isTitle") boolean isTitle);

    @Query("select count(isGreaterThanMeanMax) from TitleFeatures where isGreaterThanMeanMax =(:isGreaterThanMeanMax) and " +
            "isTitle =(:isTitle)")
    Double findIsGreaterThanMeanMaxAndIsTitle(@Param("isGreaterThanMeanMax") boolean isGreaterThanMeanMax, @Param("isTitle") boolean isTile);

    @Query("select count(isInitCap) from TitleFeatures where isInitCap =(:isInitCap) and isTitle =(:isTitle)")
    Double findIsInitCapAndIsTitle(@Param("isInitCap") boolean isInitCap, @Param("isTitle") boolean isTile);

    @Query("select count(isBold) from TitleFeatures where isBold =(:isBold) and isTitle =(:isTitle)")
    Double findIsBoldAndIsTitle(@Param("isBold") boolean isBold, @Param("isTitle") boolean isTile);

    @Query("select count(isItalic) from TitleFeatures where isItalic =(:isItalic) and isTitle =(:isTitle)")
    Double findIsItalicAndIsTitle(@Param("isItalic") boolean isItalic, @Param("isTitle") boolean isTile);

    @Query("select count(isInFirst10Rows) from TitleFeatures where isInFirst10Rows =(:isInFirst10Rows) and isTitle =(:isTitle)")
    Double findIsInFirst10RowsAndIsTitle(@Param("isInFirst10Rows") boolean isInFirst10Rows, @Param("isTitle") boolean isTile);

    @Query("select count(isAlpha) from TitleFeatures where isAlpha =(:isAlpha) and isTitle =(:isTitle)")
    Double findIsAlphaAndIsTitle(@Param("isAlpha") boolean isAlpha, @Param("isTitle") boolean isTile);

    @Query("select count(containsQuotationMark) from TitleFeatures where containsQuotationMark =(:containsQuotationMark) and isTitle =(:isTitle)")
    Double findContainsQuotationMarkAndIsTitle(@Param("containsQuotationMark") boolean containsQuotationMark, @Param("isTitle") boolean isTile);

    @Query("select count(isLengthLowerThanLengthMean) from TitleFeatures where isLengthLowerThanLengthMean =(:isLengthLowerThanLengthMean) and isTitle =(:isTitle)")
    Double findIsLengthLowerThanLengthMeanAndIsTitle(@Param("isLengthLowerThanLengthMean") boolean isLengthLowerThanLengthMean, @Param("isTitle") boolean isTile);

}
