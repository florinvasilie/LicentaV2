package com.licenta.licenta.repository;

import com.licenta.licenta.model.AuthorFeatures;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorFeaturesRepository extends CrudRepository<AuthorFeatures, Long> {

    @Query("select count (isAuthor) from AuthorFeatures  where isAuthor = (:isAuthor)")
    Double findIsAuthor(@Param("isAuthor") boolean isAuthor);

    @Query("select count(isGreaterThanMeanMax) from AuthorFeatures where isGreaterThanMeanMax =(:isGreaterThanMeanMax) and " +
            "isAuthor =(:isAuthor)")
    Double findIsGreaterThanMeanMaxAndIsAuthor(@Param("isGreaterThanMeanMax") boolean isGreaterThanMeanMax,
                                               @Param("isAuthor") boolean isAuthor);

    @Query("select count(isInitCap) from AuthorFeatures where isInitCap =(:isInitCap) and isAuthor =(:isAuthor)")
    Double findIsInitCapAndIsAuthor(@Param("isInitCap") boolean isInitCap, @Param("isAuthor") boolean isAuthor);

    @Query("select count(isBold) from AuthorFeatures where isBold =(:isBold) and isAuthor =(:isAuthor)")
    Double findIsBoldAndIsAuthor(@Param("isBold") boolean isBold, @Param("isAuthor") boolean isAuthor);

    @Query("select count(isInFirst10Rows) from AuthorFeatures where isInFirst10Rows =(:isInFirst10Rows) and isAuthor =(:isAuthor)")
    Double findIsInFirst10RowsAndIsAuthor(@Param("isInFirst10Rows") boolean isInFirst10Rows, @Param("isAuthor") boolean isAuthor);

    @Query("select count(isAlpha) from AuthorFeatures where isAlpha =(:isAlpha) and isAuthor =(:isAuthor)")
    Double findIsAlphaAndIsAuthor(@Param("isAlpha") boolean isAlpha, @Param("isAuthor") boolean isAuthor);

    @Query("select count(isFirstCharAlpha) from AuthorFeatures where isFirstCharAlpha =(:isFirstCharAlpha) and isAuthor =(:isAuthor)")
    Double findIsFirstCharAlphaAndIsAuthor(@Param("isFirstCharAlpha") boolean isFirstCharAlpha, @Param("isAuthor") boolean isAuthor);

    @Query("select count(isLastCharAlpha) from AuthorFeatures where isLastCharAlpha =(:isLastCharAlpha) and isAuthor =(:isAuthor)")
    Double findIsLastCharAlphaAndIsAuthor(@Param("isLastCharAlpha") boolean isLastCharAlpha, @Param("isAuthor") boolean isAuthor);

    @Query("select count(isAllWordsInitCap) from AuthorFeatures where isAllWordsInitCap =(:isAllWordsInitCap) and isAuthor =(:isAuthor)")
    Double findIsAllWordsInitCapAndIsAuthor(@Param("isAllWordsInitCap") boolean isAllWordsInitCap, @Param("isAuthor") boolean isAuthor);


    @Query("select count(containsBetweenTwoAndThreeWords) from AuthorFeatures where " +
            "containsBetweenTwoAndThreeWords =(:containsBetweenTwoAndThreeWords) and isAuthor =(:isAuthor)")
    Double findContainsBetweenTwoAndThreeWordsAndIsAuthor(@Param("containsBetweenTwoAndThreeWords") boolean containsBetweenTwoAndThreeWords,
                                                          @Param("isAuthor") boolean isAuthor);

}
