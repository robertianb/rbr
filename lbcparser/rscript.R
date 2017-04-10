require(tm) 
require(wordcloud)

mydata.csv <- read.csv(file='B:\\rbrouard\\eclipse_workspaces\\hmm-trunk-test\\rbr\\lbcparser\\items.csv',sep=";", quote="", header=TRUE)
mydata.vectors <- mydata.csv$title
mydata.corpus <- Corpus(VectorSource(mydata.vectors))
mydata.corpus <- tm_map(mydata.corpus, removePunctuation)
my_stopwords <- c(stopwords('french'), 'prix', 'taille', 'ans', 'bon', 'état', 'pack', 'lot', 'ensemble', 'neuf', 'mois', 'très', 'tbe', 'grand' )
mydata.corpus <- tm_map(mydata.corpus, removeWords, my_stopwords)
mydata.dtm <- TermDocumentMatrix(mydata.corpus)

findFreqTerms(mydata.dtm, lowfreq=30)

mydata.dtm2 <- removeSparseTerms(mydata.dtm, sparse=0.98)

mydata.df <- as.data.frame(inspect(mydata.dtm2))

nrow(mydata.df)
ncol(mydata.df)


# mydata.df.scale <- scale(mydata.df)
# d <- dist(mydata.df.scale, method = "euclidean") # distance matrix
# fit <- hclust(d, method="ward.D")
# plot(fit) # display dendogram?
 
# groups <- cutree(fit, k=5) # cut tree into 5 clusters
# draw dendogram with red borders around the 5 clusters
# rect.hclust(fit, k=5, border="red")

png('~/Sites/home.brouard.bzh/img/wordcloud.png')
wordcloud(mydata.corpus, scale=c(6,0.5), max.words=100, random.order=FALSE, rot.per=0.35, use.r.layout=FALSE, colors=brewer.pal(8,"Dark2"))
dev.off()