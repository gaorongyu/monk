package com.fngry.monk.biz.demo.searchengin.elasticsearch.common;

import org.elasticsearch.action.*;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.count.CountRequest;
import org.elasticsearch.action.count.CountRequestBuilder;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.exists.ExistsRequest;
import org.elasticsearch.action.exists.ExistsRequestBuilder;
import org.elasticsearch.action.exists.ExistsResponse;
import org.elasticsearch.action.explain.ExplainRequest;
import org.elasticsearch.action.explain.ExplainRequestBuilder;
import org.elasticsearch.action.explain.ExplainResponse;
import org.elasticsearch.action.fieldstats.FieldStatsRequest;
import org.elasticsearch.action.fieldstats.FieldStatsRequestBuilder;
import org.elasticsearch.action.fieldstats.FieldStatsResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.indexedscripts.delete.DeleteIndexedScriptRequest;
import org.elasticsearch.action.indexedscripts.delete.DeleteIndexedScriptRequestBuilder;
import org.elasticsearch.action.indexedscripts.delete.DeleteIndexedScriptResponse;
import org.elasticsearch.action.indexedscripts.get.GetIndexedScriptRequest;
import org.elasticsearch.action.indexedscripts.get.GetIndexedScriptRequestBuilder;
import org.elasticsearch.action.indexedscripts.get.GetIndexedScriptResponse;
import org.elasticsearch.action.indexedscripts.put.PutIndexedScriptRequest;
import org.elasticsearch.action.indexedscripts.put.PutIndexedScriptRequestBuilder;
import org.elasticsearch.action.indexedscripts.put.PutIndexedScriptResponse;
import org.elasticsearch.action.percolate.*;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.suggest.SuggestRequest;
import org.elasticsearch.action.suggest.SuggestRequestBuilder;
import org.elasticsearch.action.suggest.SuggestResponse;
import org.elasticsearch.action.termvectors.*;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.support.Headers;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.threadpool.ThreadPool;

/**
 *
 * 索引客户端
 *
 * Created by gaorongyu on 16/12/13.
 */
public class SearchEngineClient implements Client {

    private Client client = null;

    public SearchEngineClient(Client client) {
        this.client = client;
    }

    /**
     * The admin client that can be used to perform administrative operations.
     */
    @Override
    public AdminClient admin() {
        return this.client.admin();
    }

    /**
     * Index a JSON source associated with a given index and type.
     * <p/>
     * <p>The id is optional, if it is not provided, one will be generated automatically.
     *
     * @param request The index request
     * @return The result future
     * @see Requests#indexRequest(String)
     */
    @Override
    public ActionFuture<IndexResponse> index(IndexRequest request) {
        return this.client.index(request);
    }

    /**
     * Index a document associated with a given index and type.
     * <p/>
     * <p>The id is optional, if it is not provided, one will be generated automatically.
     *
     * @param request  The index request
     * @param listener A listener to be notified with a result
     * @see Requests#indexRequest(String)
     */
    @Override
    public void index(IndexRequest request, ActionListener<IndexResponse> listener) {
        this.client.index(request, listener);
    }

    /**
     * Index a document associated with a given index and type.
     * <p/>
     * <p>The id is optional, if it is not provided, one will be generated automatically.
     */
    @Override
    public IndexRequestBuilder prepareIndex() {
        return this.prepareIndex();
    }

    /**
     * Updates a document based on a script.
     *
     * @param request The update request
     * @return The result future
     */
    @Override
    public ActionFuture<UpdateResponse> update(UpdateRequest request) {
        return this.update(request);
    }

    /**
     * Updates a document based on a script.
     *
     * @param request  The update request
     * @param listener A listener to be notified with a result
     */
    @Override
    public void update(UpdateRequest request, ActionListener<UpdateResponse> listener) {
        this.client.update(request, listener);
    }

    /**
     * Updates a document based on a script.
     */
    @Override
    public UpdateRequestBuilder prepareUpdate() {
        return this.client.prepareUpdate();
    }

    /**
     * Updates a document based on a script.
     *
     * @param index
     * @param type
     * @param id
     */
    @Override
    public UpdateRequestBuilder prepareUpdate(String index, String type, String id) {
        return this.client.prepareUpdate(index, type, id);
    }

    /**
     * Index a document associated with a given index and type.
     * <p/>
     * <p>The id is optional, if it is not provided, one will be generated automatically.
     *
     * @param index The index to index the document to
     * @param type  The type to index the document to
     */
    @Override
    public IndexRequestBuilder prepareIndex(String index, String type) {
        return this.client.prepareIndex(index, type);
    }

    /**
     * Index a document associated with a given index and type.
     * <p/>
     * <p>The id is optional, if it is not provided, one will be generated automatically.
     *
     * @param index The index to index the document to
     * @param type  The type to index the document to
     * @param id    The id of the document
     */
    @Override
    public IndexRequestBuilder prepareIndex(String index, String type, @Nullable String id) {
        return this.client.prepareIndex(index, type, id);
    }

    /**
     * Deletes a document from the index based on the index, type and id.
     *
     * @param request The delete request
     * @return The result future
     * @see Requests#deleteRequest(String)
     */
    @Override
    public ActionFuture<DeleteResponse> delete(DeleteRequest request) {
        return this.client.delete(request);
    }

    /**
     * Deletes a document from the index based on the index, type and id.
     *
     * @param request  The delete request
     * @param listener A listener to be notified with a result
     * @see Requests#deleteRequest(String)
     */
    @Override
    public void delete(DeleteRequest request, ActionListener<DeleteResponse> listener) {
        this.client.delete(request, listener);
    }

    /**
     * Deletes a document from the index based on the index, type and id.
     */
    @Override
    public DeleteRequestBuilder prepareDelete() {
        return this.client.prepareDelete();
    }

    /**
     * Deletes a document from the index based on the index, type and id.
     *
     * @param index The index to delete the document from
     * @param type  The type of the document to delete
     * @param id    The id of the document to delete
     */
    @Override
    public DeleteRequestBuilder prepareDelete(String index, String type, String id) {
        return this.client.prepareDelete(index, type, id);
    }

    /**
     * Executes a bulk of index / delete operations.
     *
     * @param request The bulk request
     * @return The result future
     * @see org.elasticsearch.client.Requests#bulkRequest()
     */
    @Override
    public ActionFuture<BulkResponse> bulk(BulkRequest request) {
        return this.client.bulk(request);
    }

    /**
     * Executes a bulk of index / delete operations.
     *
     * @param request  The bulk request
     * @param listener A listener to be notified with a result
     * @see org.elasticsearch.client.Requests#bulkRequest()
     */
    @Override
    public void bulk(BulkRequest request, ActionListener<BulkResponse> listener) {
        this.client.bulk(request, listener);
    }

    /**
     * Executes a bulk of index / delete operations.
     */
    @Override
    public BulkRequestBuilder prepareBulk() {
        return this.client.prepareBulk();
    }

    /**
     * Gets the document that was indexed from an index with a type and id.
     *
     * @param request The get request
     * @return The result future
     * @see Requests#getRequest(String)
     */
    @Override
    public ActionFuture<GetResponse> get(GetRequest request) {
        return this.client.get(request);
    }

    /**
     * Gets the document that was indexed from an index with a type and id.
     *
     * @param request  The get request
     * @param listener A listener to be notified with a result
     * @see Requests#getRequest(String)
     */
    @Override
    public void get(GetRequest request, ActionListener<GetResponse> listener) {
        this.client.get(request, listener);
    }

    /**
     * Gets the document that was indexed from an index with a type and id.
     */
    @Override
    public GetRequestBuilder prepareGet() {
        return this.client.prepareGet();
    }

    /**
     * Gets the document that was indexed from an index with a type (optional) and id.
     *
     * @param index
     * @param type
     * @param id
     */
    @Override
    public GetRequestBuilder prepareGet(String index, @Nullable String type, String id) {
        return this.client.prepareGet(index, type, id);
    }

    /**
     * Put an indexed script
     */
    @Override
    public PutIndexedScriptRequestBuilder preparePutIndexedScript() {
        return this.client.preparePutIndexedScript();
    }

    /**
     * Put the indexed script
     *
     * @param scriptLang
     * @param id
     * @param source
     */
    @Override
    public PutIndexedScriptRequestBuilder preparePutIndexedScript(@Nullable String scriptLang, String id, String source) {
        return this.client.preparePutIndexedScript(scriptLang, id, source);
    }

    /**
     * delete an indexed script
     *
     * @param request
     * @param listener
     */
    @Override
    public void deleteIndexedScript(DeleteIndexedScriptRequest request, ActionListener<DeleteIndexedScriptResponse> listener) {
        this.client.deleteIndexedScript(request, listener);
    }

    /**
     * Delete an indexed script
     *
     * @param request The put request
     * @return The result future
     */
    @Override
    public ActionFuture<DeleteIndexedScriptResponse> deleteIndexedScript(DeleteIndexedScriptRequest request) {
        return this.client.deleteIndexedScript(request);
    }

    /**
     * Delete an indexed script
     */
    @Override
    public DeleteIndexedScriptRequestBuilder prepareDeleteIndexedScript() {
        return this.client.prepareDeleteIndexedScript();
    }

    /**
     * Delete an indexed script
     *
     * @param scriptLang
     * @param id
     */
    @Override
    public DeleteIndexedScriptRequestBuilder prepareDeleteIndexedScript(@Nullable String scriptLang, String id) {
        return this.client.prepareDeleteIndexedScript(scriptLang, id);
    }

    /**
     * Put an indexed script
     *
     * @param request
     * @param listener
     */
    @Override
    public void putIndexedScript(PutIndexedScriptRequest request, ActionListener<PutIndexedScriptResponse> listener) {
        this.client.putIndexedScript(request, listener);
    }

    /**
     * Put an indexed script
     *
     * @param request The put request
     * @return The result future
     */
    @Override
    public ActionFuture<PutIndexedScriptResponse> putIndexedScript(PutIndexedScriptRequest request) {
        return this.client.putIndexedScript(request);
    }

    /**
     * Get an indexed script
     */
    @Override
    public GetIndexedScriptRequestBuilder prepareGetIndexedScript() {
        return this.client.prepareGetIndexedScript();
    }

    /**
     * Get the indexed script
     *
     * @param scriptLang
     * @param id
     */
    @Override
    public GetIndexedScriptRequestBuilder prepareGetIndexedScript(@Nullable String scriptLang, String id) {
        return this.client.prepareGetIndexedScript(scriptLang, id);
    }

    /**
     * Get an indexed script
     *
     * @param request
     * @param listener
     */
    @Override
    public void getIndexedScript(GetIndexedScriptRequest request, ActionListener<GetIndexedScriptResponse> listener) {
        this.client.getIndexedScript(request, listener);
    }

    /**
     * Gets the document that was indexed from an index with a type and id.
     *
     * @param request The get request
     * @return The result future
     * @see Requests#getRequest(String)
     */
    @Override
    public ActionFuture<GetIndexedScriptResponse> getIndexedScript(GetIndexedScriptRequest request) {
        return this.client.getIndexedScript(request);
    }

    /**
     * Multi get documents.
     *
     * @param request
     */
    @Override
    public ActionFuture<MultiGetResponse> multiGet(MultiGetRequest request) {
        return this.client.multiGet(request);
    }

    /**
     * Multi get documents.
     *
     * @param request
     * @param listener
     */
    @Override
    public void multiGet(MultiGetRequest request, ActionListener<MultiGetResponse> listener) {
        this.client.multiGet(request, listener);
    }

    /**
     * Multi get documents.
     */
    @Override
    public MultiGetRequestBuilder prepareMultiGet() {
        return this.client.prepareMultiGet();
    }

    /**
     * A count of all the documents matching a specific query.
     *
     * @param request The count request
     * @return The result future
     * @see Requests#countRequest(String...)
     * @deprecated use {@link #search(SearchRequest)} instead and set size to 0
     */
    @Override
    public ActionFuture<CountResponse> count(CountRequest request) {
        return this.client.count(request);
    }

    /**
     * A count of all the documents matching a specific query.
     *
     * @param request  The count request
     * @param listener A listener to be notified of the result
     * @see Requests#countRequest(String...)
     * @deprecated use {@link #search(SearchRequest, org.elasticsearch.action.ActionListener)} instead and set size to 0
     */
    @Override
    public void count(CountRequest request, ActionListener<CountResponse> listener) {
        this.client.count(request, listener);
    }

    /**
     * A count of all the documents matching a specific query.
     *
     * @param indices
     * @deprecated use {@link #prepareSearch(String...)} instead and set size to 0
     */
    @Override
    public CountRequestBuilder prepareCount(String... indices) {
        return this.client.prepareCount(indices);
    }

    /**
     * Checks existence of any documents matching a specific query.
     *
     * @param request The exists request
     * @return The result future
     * @deprecated use {@link #search(SearchRequest)} instead and set `size` to `0` and `terminate_after` to `1`
     */
    @Override
    public ActionFuture<ExistsResponse> exists(ExistsRequest request) {
        return this.client.exists(request);
    }

    /**
     * Checks existence of any documents matching a specific query.
     *
     * @param request  The exists request
     * @param listener A listener to be notified of the result
     * @deprecated use {@link #search(SearchRequest, org.elasticsearch.action.ActionListener)} instead and set `size` to `0` and `terminate_after` to `1`
     */
    @Override
    public void exists(ExistsRequest request, ActionListener<ExistsResponse> listener) {
        this.client.exists(request, listener);
    }

    /**
     * Checks existence of any documents matching a specific query.
     *
     * @param indices
     * @deprecated use {@link #prepareSearch(String...)} instead and set `size` to `0` and `terminate_after` to `1`
     */
    @Override
    public ExistsRequestBuilder prepareExists(String... indices) {
        return this.client.prepareExists(indices);
    }

    /**
     * Suggestion matching a specific phrase.
     *
     * @param request The suggest request
     * @return The result future
     * @see Requests#suggestRequest(String...)
     */
    @Override
    public ActionFuture<SuggestResponse> suggest(SuggestRequest request) {
        return this.client.suggest(request);
    }

    /**
     * Suggestions matching a specific phrase.
     *
     * @param request  The suggest request
     * @param listener A listener to be notified of the result
     * @see Requests#suggestRequest(String...)
     */
    @Override
    public void suggest(SuggestRequest request, ActionListener<SuggestResponse> listener) {
        this.client.suggest(request, listener);
    }

    /**
     * Suggestions matching a specific phrase.
     *
     * @param indices
     */
    @Override
    public SuggestRequestBuilder prepareSuggest(String... indices) {
        return this.client.prepareSuggest(indices);
    }

    /**
     * Search across one or more indices and one or more types with a query.
     *
     * @param request The search request
     * @return The result future
     * @see Requests#searchRequest(String...)
     */
    @Override
    public ActionFuture<SearchResponse> search(SearchRequest request) {
        return this.client.search(request);
    }

    /**
     * Search across one or more indices and one or more types with a query.
     *
     * @param request  The search request
     * @param listener A listener to be notified of the result
     * @see Requests#searchRequest(String...)
     */
    @Override
    public void search(SearchRequest request, ActionListener<SearchResponse> listener) {
        this.client.search(request, listener);
    }

    /**
     * Search across one or more indices and one or more types with a query.
     *
     * @param indices
     */
    @Override
    public SearchRequestBuilder prepareSearch(String... indices) {
        return this.client.prepareSearch(indices);
    }

    /**
     * A search scroll request to continue searching a previous scrollable search request.
     *
     * @param request The search scroll request
     * @return The result future
     * @see Requests#searchScrollRequest(String)
     */
    @Override
    public ActionFuture<SearchResponse> searchScroll(SearchScrollRequest request) {
        return this.client.searchScroll(request);
    }

    /**
     * A search scroll request to continue searching a previous scrollable search request.
     *
     * @param request  The search scroll request
     * @param listener A listener to be notified of the result
     * @see Requests#searchScrollRequest(String)
     */
    @Override
    public void searchScroll(SearchScrollRequest request, ActionListener<SearchResponse> listener) {
        this.client.searchScroll(request, listener);
    }

    /**
     * A search scroll request to continue searching a previous scrollable search request.
     *
     * @param scrollId
     */
    @Override
    public SearchScrollRequestBuilder prepareSearchScroll(String scrollId) {
        return this.client.prepareSearchScroll(scrollId);
    }

    /**
     * Performs multiple search requests.
     *
     * @param request
     */
    @Override
    public ActionFuture<MultiSearchResponse> multiSearch(MultiSearchRequest request) {
        return this.client.multiSearch(request);
    }

    /**
     * Performs multiple search requests.
     *
     * @param request
     * @param listener
     */
    @Override
    public void multiSearch(MultiSearchRequest request, ActionListener<MultiSearchResponse> listener) {
        this.client.multiSearch(request, listener);
    }

    /**
     * Performs multiple search requests.
     */
    @Override
    public MultiSearchRequestBuilder prepareMultiSearch() {
        return this.client.prepareMultiSearch();
    }

    /**
     * An action that returns the term vectors for a specific document.
     *
     * @param request The term vector request
     * @return The response future
     */
    @Override
    public ActionFuture<TermVectorsResponse> termVectors(TermVectorsRequest request) {
        return this.client.termVectors(request);
    }

    /**
     * An action that returns the term vectors for a specific document.
     *
     * @param request  The term vector request
     * @param listener
     * @return The response future
     */
    @Override
    public void termVectors(TermVectorsRequest request, ActionListener<TermVectorsResponse> listener) {
        this.client.termVectors(request, listener);
    }

    /**
     * Builder for the term vector request.
     */
    @Override
    public TermVectorsRequestBuilder prepareTermVectors() {
        return this.client.prepareTermVectors();
    }

    /**
     * Builder for the term vector request.
     *
     * @param index The index to load the document from
     * @param type  The type of the document
     * @param id    The id of the document
     */
    @Override
    public TermVectorsRequestBuilder prepareTermVectors(String index, String type, String id) {
        return this.client.prepareTermVectors(index, type, id);
    }

    /**
     * An action that returns the term vectors for a specific document.
     *
     * @param request The term vector request
     * @return The response future
     */
    @Override
    public ActionFuture<TermVectorsResponse> termVector(TermVectorsRequest request) {
        return this.client.termVectors(request);
    }

    /**
     * An action that returns the term vectors for a specific document.
     *
     * @param request  The term vector request
     * @param listener
     * @return The response future
     */
    @Override
    public void termVector(TermVectorsRequest request, ActionListener<TermVectorsResponse> listener) {
        this.termVectors(request, listener);
    }

    /**
     * Builder for the term vector request.
     */
    @Override
    public TermVectorsRequestBuilder prepareTermVector() {
        return this.client.prepareTermVectors();
    }

    /**
     * Builder for the term vector request.
     *
     * @param index The index to load the document from
     * @param type  The type of the document
     * @param id    The id of the document
     */
    @Override
    public TermVectorsRequestBuilder prepareTermVector(String index, String type, String id) {
        return this.client.prepareTermVectors(index, type, id);
    }

    /**
     * Multi get term vectors.
     *
     * @param request
     */
    @Override
    public ActionFuture<MultiTermVectorsResponse> multiTermVectors(MultiTermVectorsRequest request) {
        return this.client.multiTermVectors(request);
    }

    /**
     * Multi get term vectors.
     *
     * @param request
     * @param listener
     */
    @Override
    public void multiTermVectors(MultiTermVectorsRequest request, ActionListener<MultiTermVectorsResponse> listener) {
        this.client.multiTermVectors(request, listener);
    }

    /**
     * Multi get term vectors.
     */
    @Override
    public MultiTermVectorsRequestBuilder prepareMultiTermVectors() {
        return this.client.prepareMultiTermVectors();
    }

    /**
     * Percolates a request returning the matches documents.
     *
     * @param request
     */
    @Override
    public ActionFuture<PercolateResponse> percolate(PercolateRequest request) {
        return this.client.percolate(request);
    }

    /**
     * Percolates a request returning the matches documents.
     *
     * @param request
     * @param listener
     */
    @Override
    public void percolate(PercolateRequest request, ActionListener<PercolateResponse> listener) {
        this.client.percolate(request, listener);
    }

    /**
     * Percolates a request returning the matches documents.
     */
    @Override
    public PercolateRequestBuilder preparePercolate() {
        return this.client.preparePercolate();
    }

    /**
     * Performs multiple percolate requests.
     *
     * @param request
     */
    @Override
    public ActionFuture<MultiPercolateResponse> multiPercolate(MultiPercolateRequest request) {
        return this.client.multiPercolate(request);
    }

    /**
     * Performs multiple percolate requests.
     *
     * @param request
     * @param listener
     */
    @Override
    public void multiPercolate(MultiPercolateRequest request, ActionListener<MultiPercolateResponse> listener) {
        this.client.multiPercolate(request, listener);
    }

    /**
     * Performs multiple percolate requests.
     */
    @Override
    public MultiPercolateRequestBuilder prepareMultiPercolate() {
        return this.client.prepareMultiPercolate();
    }

    /**
     * Computes a score explanation for the specified request.
     *
     * @param index The index this explain is targeted for
     * @param type  The type this explain is targeted for
     * @param id    The document identifier this explain is targeted for
     */
    @Override
    public ExplainRequestBuilder prepareExplain(String index, String type, String id) {
        return this.client.prepareExplain(index, type, id);
    }

    /**
     * Computes a score explanation for the specified request.
     *
     * @param request The request encapsulating the query and document identifier to compute a score explanation for
     */
    @Override
    public ActionFuture<ExplainResponse> explain(ExplainRequest request) {
        return this.client.explain(request);
    }

    /**
     * Computes a score explanation for the specified request.
     *
     * @param request  The request encapsulating the query and document identifier to compute a score explanation for
     * @param listener A listener to be notified of the result
     */
    @Override
    public void explain(ExplainRequest request, ActionListener<ExplainResponse> listener) {
        this.client.explain(request, listener);
    }

    /**
     * Clears the search contexts associated with specified scroll ids.
     */
    @Override
    public ClearScrollRequestBuilder prepareClearScroll() {
        return this.client.prepareClearScroll();
    }

    /**
     * Clears the search contexts associated with specified scroll ids.
     *
     * @param request
     */
    @Override
    public ActionFuture<ClearScrollResponse> clearScroll(ClearScrollRequest request) {
        return this.client.clearScroll(request);
    }

    /**
     * Clears the search contexts associated with specified scroll ids.
     *
     * @param request
     * @param listener
     */
    @Override
    public void clearScroll(ClearScrollRequest request, ActionListener<ClearScrollResponse> listener) {
        this.client.clearScroll(request, listener);
    }

    @Override
    public FieldStatsRequestBuilder prepareFieldStats() {
        return this.client.prepareFieldStats();
    }

    @Override
    public ActionFuture<FieldStatsResponse> fieldStats(FieldStatsRequest request) {
        return this.client.fieldStats(request);
    }

    @Override
    public void fieldStats(FieldStatsRequest request, ActionListener<FieldStatsResponse> listener) {
        this.client.fieldStats(request, listener);
    }

    /**
     * Returns this clients settings
     */
    @Override
    public Settings settings() {
        return client.settings();
    }

    @Override
    public Headers headers() {
        return client.headers();
    }

    /**
     * Executes a generic action, denoted by an {@link org.elasticsearch.action.Action}.
     *
     * @param action  The action type to execute.
     * @param request The action request.
     * @return A future allowing to get back the response.
     */
    @Override
    public <Request extends ActionRequest, Response extends ActionResponse, RequestBuilder extends ActionRequestBuilder<Request, Response, RequestBuilder>> ActionFuture<Response> execute(Action<Request, Response, RequestBuilder> action, Request request) {
        return this.client.execute(action, request);
    }

    /**
     * Executes a generic action, denoted by an {@link org.elasticsearch.action.Action}.
     *
     * @param action   The action type to execute.
     * @param request  The action request.
     * @param listener The listener to receive the response back.
     */
    @Override
    public <Request extends ActionRequest, Response extends ActionResponse, RequestBuilder extends ActionRequestBuilder<Request, Response, RequestBuilder>> void execute(Action<Request, Response, RequestBuilder> action, Request request, ActionListener<Response> listener) {
        this.client.execute(action, request, listener);
    }

    /**
     * Prepares a request builder to execute, specified by {@link org.elasticsearch.action.Action}.
     *
     * @param action The action type to execute.
     * @return The request builder, that can, at a later stage, execute the request.
     */
    @Override
    public <Request extends ActionRequest, Response extends ActionResponse, RequestBuilder extends ActionRequestBuilder<Request, Response, RequestBuilder>> RequestBuilder prepareExecute(Action<Request, Response, RequestBuilder> action) {
        return client.prepareExecute(action);
    }

    /**
     * Returns the threadpool used to execute requests on this client
     */
    @Override
    public ThreadPool threadPool() {
        return client.threadPool();
    }

    @Override
    public void close() {
        this.client.close();
    }

}
