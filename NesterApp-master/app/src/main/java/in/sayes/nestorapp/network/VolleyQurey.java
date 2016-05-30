package in.sayes.nestorapp.network;

/**
 * Created by sourav on 04/05/16.
 * Project : NesterApp , Package Name : in.sayes.nestorapp.network
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
/**
 * Created by oscarrodriguez on 6/11/14. https://github.com/koombea/volley
 */
public class VolleyQurey {

  /*  private static final String JEXO_QUERY = "JexoQuery";
    protected PerformRequest performRequest;
    private HashMap<String, String> mIds;
    private HashMap<String, String> mKeys;
    private Map<String, String> mRestHeaderKeys;
    private VolleyClient volleyClient;
    private String TAG = VolleyQuery.class.getName();
    private HashMap<String, Object> paramsMethod;

    @Deprecated
    public VolleyQuery() {
        this.mIds = new HashMap<>();
        this.mKeys = new HashMap<>();
        this.mRestHeaderKeys = new HashMap<>();
        this.performRequest = new PerformRequest(null);
        this.performRequest.mRequestTimeOut = VolleyConstants.REQUEST_TIME_OUT;
        this.volleyClient = VolleyClient.getInstance();
    }

    public VolleyQuery(VolleyCallback callback) {
        this.mIds = new HashMap<>();
        this.mKeys = new HashMap<>();
        this.mRestHeaderKeys = new HashMap<>();
        this.performRequest = new PerformRequest(callback);
        this.performRequest.mRequestTimeOut = VolleyConstants.REQUEST_TIME_OUT;
        this.volleyClient = VolleyClient.getInstance();
    }

    public void setBasicAuth(String username, String password) {
        HashMap<String, String> serverConfig = new HashMap<>();
        serverConfig.put(VolleyConstants.API_BASIC_AUTH_PASSWORD, username);
        serverConfig.put(VolleyConstants.API_BASIC_AUTH_USERNAME, password);
        serverConfig.put(VolleyConstants.API_BASIC_AUTH_ENABLED,
                VolleyConstants.API_BASIC_AUTH_ENABLE_TRUE);
        volleyClient.addRestConfigValue(VolleyConstants.API_BASIC_AUTH, serverConfig);
    }

    public void setHeaders(String key, String value) {
        volleyClient.setHeader(key, value);
    }

    public void seTimeOut(int time) {
        performRequest.mRequestTimeOut = time;
    }

    public String setPathId(String key, String value) {
        mIds.put(key, value);
        return mIds.get(key);
    }

    public void addUrlParameter(String key, String value) {
        mKeys.put(key, value);
    }

    private String getContentType() {
        return paramsMethod.get(VolleyConstants.REST_CONTENT_TYPE).toString();
    }

    public void setMethod(String method) {
        this.paramsMethod = (HashMap<String, Object>) volleyClient.getMethods().get(method);
    }

    private String getTagRequest() {
        return paramsMethod.get(VolleyConstants.REST_TAG).toString();
    }

    private String getHost() {
        return paramsMethod.get(VolleyConstants.REST_HOST).toString();
    }

    private String getRestUrl() {
        return getRestUrl(null);
    }

    protected String getKeyParameters() {
        String query = "";
        String format;

        boolean first = true;

        if (mKeys != null && !mKeys.isEmpty()) {

            for (String key : mKeys.keySet()) {
                format = (first ? "?" : "&");
                String param = format + key + "=" + URLEncoder.encode(mKeys.get(key));
                query = query + param;
                first = false;
            }
        }

        return query;
    }

    private String getRestUrl(VolleyModel model) {
        String url = "";
        try {

            String serverUrl = "", subdomain;

            if (!getHost().isEmpty()) {
                serverUrl += getHost();
            } else {
                subdomain = (volleyClient.getSubdomain() != null) ? volleyClient.getSubdomain() + "." : "";
                serverUrl = volleyClient.getProtocol() + "://" + subdomain
                        + volleyClient.getRestConfig().get(VolleyConstants.REST_URL).toString();
            }

            String path = paramsMethod.get(VolleyConstants.REST_PATH).toString();


            if (model != null) {
                this.mIds = model.toParamId(this.mIds);
                this.mKeys = model.toUrlParamMap(this.mKeys);
            }

            String urlKeys = getKeyParameters();

            if (!urlKeys.isEmpty()) {
                path = path + urlKeys;
            }

            if (this.mIds != null) {
                for (String key : mIds.keySet()) {
                    path = path.replace(":" + key, mIds.get(key));
                }
            }

            serverUrl = (serverUrl.substring(serverUrl.length() - 1,
                    serverUrl.length()).equals("/"))
                    ? serverUrl.substring(0, serverUrl.length() - 1) : serverUrl;
            if (path != null && !path.isEmpty())
                path = (path.substring(0, 1).equals("/")) ? path.substring(1, path.length()) : path;


            url = serverUrl + ((path != null && !path.isEmpty()) ? "/" + path : "");


        } catch (Exception e) {
            Log.d(JEXO_QUERY, "Problem loading the rest url values.");
        }

        return url;
    }

    private int getServerMethod() {

        String method = paramsMethod.get(VolleyConstants.REST_METHOD).toString();
        int flag;

        switch (method) {
            case VolleyConstants.METHOD_POST:
                flag = Request.Method.POST;
                break;

            case VolleyConstants.METHOD_PUT:
                flag = Request.Method.PUT;
                break;

            case VolleyConstants.METHOD_DELETE:
                flag = Request.Method.DELETE;
                break;

            case VolleyConstants.METHOD_PATCH:
                flag = Request.Method.PATCH;
                break;
            default:
                flag = Request.Method.DEPRECATED_GET_OR_POST;
                break;
        }

        return flag;

    }

    private void find(VolleyModel model) {
        Request request = performRequest.configurateRequest(getContentType(),
                model, getRestUrl(model)).doRequest();
        request.setTag(getTagRequest());
        addRequest(request);
    }

    @Deprecated
    public void find(String method, VolleyCallback callback) {
        performRequest.callback = callback;
        setMethod(method);
        find(null);
    }

    public void findString() {
        Request request = performRequest.configurateRequest(getRestUrl()).doRequestString();
        request.setTag(getTagRequest());
        addRequest(request);
    }

    private void addRequest(Request request) {
        volleyClient.addToRequestQueue(request);
    }

    @Deprecated
    public void findString(String method, VolleyCallback callback) {
        performRequest.callback = callback;
        setMethod(method);
        findString();
    }

    public void save(String method, HashMap<String, Object> params) {
        setMethod(method);
        if (getServerMethod() != Request.Method.DEPRECATED_GET_OR_POST) {
            Request request = performRequest.configurateRequest(getServerMethod(),
                    getContentType(), getRestUrl(), params).doRequest();
            request.setTag(method);
            addRequest(request);
        } else {
            Log.d("Response", "The method to this request is not defined");
        }
    }

    public void save(String method, VolleyModel model) {
        save(method, model.getParams());
    }

    @Deprecated
    public void save(String method, VolleyModel params, VolleyCallback volleyCallback) {
        performRequest.callback = volleyCallback;
        save(method, params);
    }

    @Deprecated
    public void save(String method, HashMap<String, Object> params, VolleyCallback volleyCallback) {
        performRequest.callback = volleyCallback;
        save(method, params);
    }
*/
}