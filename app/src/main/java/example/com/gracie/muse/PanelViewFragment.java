package example.com.gracie.muse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PanelViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PanelViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PanelViewFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_BLURB = "argblurb";
    private static final String ARG_IMGID = "argimgid";
    private static final String ARG_IMGPATH = "arimgpath";
    private static final String ARG_CREATOR = "argcreator";


    // TODO: Rename and change types of parameters
    private String mCreatorUsername;
    private String mBlurb;
    private String mImagePath;
    private int mImageID;

    /*
    private OnFragmentInteractionListener mListener;

    public PanelView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PanelView.
     */



    // TODO: Rename and change types and number of parameters
    public static PanelViewFragment newInstance(String creatorUsername, String blurb,
                                                int imgID, String imgPath) {
        PanelViewFragment fragment = new PanelViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CREATOR, creatorUsername);
        args.putString(ARG_BLURB, blurb);
        args.putInt(ARG_IMGID, imgID);
        args.putString(ARG_IMGPATH, imgPath);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCreatorUsername = getArguments().getString(ARG_CREATOR);
            mBlurb = getArguments().getString(ARG_BLURB);
            mImagePath = getArguments().getString(ARG_IMGPATH);
            mImageID = getArguments().getInt(ARG_IMGID);
            Log.d("strips", "Username=" + mCreatorUsername + " Blurb=" + mBlurb +
                    " Image Path=" + mImagePath);



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_panel_view, container, false);

        Log.d("strips", "INSIDE PANELVIEW");
        TextView tv = (TextView) view.findViewById(R.id.blurb_text);
        tv.setText(mBlurb);
        return view;
    }
/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
/*    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
*/
}
