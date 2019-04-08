package com.zejian.emotionkeyboard.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.zejian.emotionkeyboard.R;
import com.zejian.emotionkeyboard.adapter.EvenItemDecoration;
import com.zejian.emotionkeyboard.adapter.HorizontalRecyclerviewAdapter;
import com.zejian.emotionkeyboard.adapter.MoreOptionAdapter;
import com.zejian.emotionkeyboard.adapter.NoHorizontalScrollerVPAdapter;
import com.zejian.emotionkeyboard.emotionkeyboardview.EmotionKeyboard;
import com.zejian.emotionkeyboard.emotionkeyboardview.NoHorizontalScrollerViewPager;
import com.zejian.emotionkeyboard.model.ImageModel;
import com.zejian.emotionkeyboard.utils.ChatMsgType;
import com.zejian.emotionkeyboard.utils.DataUtils;
import com.zejian.emotionkeyboard.utils.DisplayUtils;
import com.zejian.emotionkeyboard.utils.EmotionUtils;
import com.zejian.emotionkeyboard.utils.GlobalOnItemClickManagerUtils;
import com.zejian.emotionkeyboard.utils.ScreenUtils;
import com.zejian.emotionkeyboard.utils.SharedPreferencedUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zejian
 * Time  16/1/6 下午5:26
 * Email shinezejian@163.com
 * Description:表情主界面
 */
public class EmotionMainFragment extends BaseFragment {

    //是否绑定当前Bar的编辑框的flag
    public static final String BIND_TO_EDITTEXT="bind_to_edittext";
    //是否隐藏bar上的编辑框和发生按钮
    public static final String HIDE_BAR_EDITTEXT_AND_BTN="hide bar's editText and btn";

    //当前被选中底部tab
    private static final String CURRENT_POSITION_FLAG="CURRENT_POSITION_FLAG";
    private int CurrentPosition=0;
    //底部水平tab
    private RecyclerView recyclerview_horizontal;
    private HorizontalRecyclerviewAdapter horizontalRecyclerviewAdapter;
    //表情面板
    private EmotionKeyboard mEmotionKeyboard;

    public EditText bar_edit_text;
    private ImageView bar_image_voice_btn;
    private Button bar_btn_send;
    private LinearLayout rl_editbar_bg;

    //需要绑定的内容view
    private View contentView;

    //不可横向滚动的ViewPager
    private NoHorizontalScrollerViewPager viewPager;

    //是否绑定当前Bar的编辑框,默认true,即绑定。
    //false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
    private boolean isBindToBarEditText=true;

    //是否隐藏bar上的编辑框和发生按钮,默认不隐藏
    private boolean isHidenBarEditTextAndBtn=false;

    List<Fragment> fragments=new ArrayList<>();


    /**
     * 创建与Fragment对象关联的View视图时调用
     * @param inflater i
     * @param container c
     * @param savedInstanceState  s
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_emotion, container, false);
        isHidenBarEditTextAndBtn= args.getBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN);
        //获取判断绑定对象的参数
        isBindToBarEditText=args.getBoolean(EmotionMainFragment.BIND_TO_EDITTEXT);
        initView(rootView);
        if (getActivity() != null) {
            mEmotionKeyboard = EmotionKeyboard.with(getActivity())
                    .bindToSwicher(mMoreEmotionSwitcher)
                    .setEmotionView(rootView.findViewById(R.id.ll_emotion_layout))//绑定表情面板
                    .bindToContent(contentView)//绑定内容view
                    .bindToEditText(!isBindToBarEditText ? ((EditText) contentView) : ((EditText) rootView.findViewById(R.id.bar_edit_text)))//判断绑定那种EditView
                    .bindToEmotionButton(rootView.findViewById(R.id.emotion_button))//绑定表情按钮
                    .bindToMoreOptionButton(rootView.findViewById(R.id.btn_more))
                    .build();
        }
        initListener();
        initMoreOptionData();
        initDatas();
        //创建全局监听
        GlobalOnItemClickManagerUtils globalOnItemClickManager= GlobalOnItemClickManagerUtils.getInstance(getActivity());

        if(isBindToBarEditText){
            //绑定当前Bar的编辑框
            globalOnItemClickManager.attachToEditText(bar_edit_text);

        }else{
            // false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
            globalOnItemClickManager.attachToEditText((EditText) contentView);
            mEmotionKeyboard.bindToEditText((EditText)contentView);
        }
        return rootView;
    }

    private void initMoreOptionData() {
        if (getActivity() != null) {
            List<ChatMsgType> chatOptionDataListByType = DataUtils.getChatOptionDataListByType(getActivity(),
                    true);
            PagerSnapHelper linearSnapHelper = new PagerSnapHelper();
            linearSnapHelper.attachToRecyclerView(mOptionRe);
            MoreOptionAdapter moreOptionAdapter = new MoreOptionAdapter(chatOptionDataListByType);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4,
                    RecyclerView.VERTICAL, false);
//            final float scale = getContext().getResources().getDisplayMetrics().density;
//            int i = (int) (26 * scale + 0.5f);
            int screenWidth = ScreenUtils.getScreenWidth(getContext());
            int itemWidtg = DisplayUtils.dp2px(getContext(), 90);
            mOptionRe.addItemDecoration(new EvenItemDecoration((screenWidth-itemWidtg*4)/6,3));
            mOptionRe.setLayoutManager(gridLayoutManager);

            mOptionRe.setAdapter(moreOptionAdapter);
        }

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 绑定内容view
     * @param contentView content
     * @return
     */
    public void bindToContentView(View contentView){
        this.contentView=contentView;
    }

    private ViewSwitcher mViewSwitch;
    private ViewSwitcher mVKeySwitcher;
    private ImageButton mChattingModeKeybroadBtn;
    private ViewSwitcher mEtRbSwitcher;
    private Button mBtnMore;
    private ViewSwitcher mMoreEmotionSwitcher;
    private RecyclerView mOptionRe;

    /**
     * 初始化view控件
     */
    protected void initView(View rootView){
        viewPager = rootView.findViewById(R.id.vp_emotionview_layout);
        recyclerview_horizontal = rootView.findViewById(R.id.recyclerview_horizontal);
        bar_edit_text = rootView.findViewById(R.id.bar_edit_text);
        bar_image_voice_btn = rootView.findViewById(R.id.bar_image_voice);
        mChattingModeKeybroadBtn = rootView.findViewById(R.id.chatting_mode_keybroad_btn);
        bar_btn_send = rootView.findViewById(R.id.bar_btn_send);
        rl_editbar_bg = rootView.findViewById(R.id.rl_editbar_bg);

        mOptionRe = rootView.findViewById(R.id.option_re);

        mMoreEmotionSwitcher = rootView.findViewById(R.id.more_emotion_switcher);

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3,
//                GridLayoutManager.HORIZONTAL,false);
        mBtnMore = rootView.findViewById(R.id.btn_more);

        mEtRbSwitcher = rootView.findViewById(R.id.et_rb_switcher);


        mViewSwitch = rootView.findViewById(R.id.view_switch);

        mVKeySwitcher = rootView.findViewById(R.id.v_key_switcher);

        bar_image_voice_btn.setOnClickListener(v -> {
            mVKeySwitcher.setDisplayedChild(1);
            mEtRbSwitcher.setDisplayedChild(1);
            mEmotionKeyboard.hideSoftInput();
            mEmotionKeyboard.hideEmotionLayout(false);
        });

        mChattingModeKeybroadBtn.setOnClickListener(v -> {
            mVKeySwitcher.setDisplayedChild(0);
            mEtRbSwitcher.setDisplayedChild(0);
            mEmotionKeyboard.hideEmotionLayout(true);
            mEmotionKeyboard.showSoftInput();
        });
        mViewSwitch.setDisplayedChild(0);
        if(isHidenBarEditTextAndBtn){//隐藏
            bar_edit_text.setVisibility(View.GONE);
            bar_image_voice_btn.setVisibility(View.GONE);
            bar_btn_send.setVisibility(View.GONE);
            rl_editbar_bg.setBackgroundResource(R.color.bg_edittext_color);
        }else{
//            bar_edit_text.setVisibility(View.VISIBLE);
//            bar_image_voice_btn.setVisibility(View.VISIBLE);
//            bar_btn_send.setVisibility(View.VISIBLE);
            rl_editbar_bg.setBackgroundResource(R.drawable.shape_bg_reply_edittext);
        }
        bar_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (bar_edit_text.getText().length() > 0) {

                    mViewSwitch.setDisplayedChild(1);
                } else {
                    mViewSwitch.setDisplayedChild(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 初始化监听器
     */
    protected void initListener(){

    }

    /**
     * 数据操作,这里是测试数据，请自行更换数据
     */
    protected void initDatas(){
        replaceFragment();
        List<ImageModel> list = new ArrayList<>();
        for (int i=0 ; i<fragments.size(); i++){
            if(i==0){
                ImageModel model1=new ImageModel();
                model1.icon= getResources().getDrawable(R.drawable.ic_emotion);
                model1.flag="经典笑脸";
                model1.isSelected=true;
                list.add(model1);
            }else {
                ImageModel model = new ImageModel();
                model.icon = getResources().getDrawable(R.drawable.ic_plus);
                model.flag = "其他笑脸" + i;
                model.isSelected = false;
                list.add(model);
            }
        }

        //记录底部默认选中第一个
        CurrentPosition=0;
        SharedPreferencedUtils.setInteger(getActivity(), CURRENT_POSITION_FLAG, CurrentPosition);

        //底部tab
        horizontalRecyclerviewAdapter = new HorizontalRecyclerviewAdapter(getActivity(),list);
        recyclerview_horizontal.setHasFixedSize(true);//使RecyclerView保持固定的大小,这样会提高RecyclerView的性能
        recyclerview_horizontal.setAdapter(horizontalRecyclerviewAdapter);
        recyclerview_horizontal.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));
        //初始化recyclerview_horizontal监听器
        horizontalRecyclerviewAdapter.setOnClickItemListener(new HorizontalRecyclerviewAdapter.OnClickItemListener() {
            @Override
            public void onItemClick(View view, int position, List<ImageModel> datas) {
                //获取先前被点击tab
                int oldPosition = SharedPreferencedUtils.getInteger(getActivity(), CURRENT_POSITION_FLAG, 0);
                //修改背景颜色的标记
                datas.get(oldPosition).isSelected = false;
                //记录当前被选中tab下标
                CurrentPosition = position;
                datas.get(CurrentPosition).isSelected = true;
                SharedPreferencedUtils.setInteger(getActivity(), CURRENT_POSITION_FLAG, CurrentPosition);
                //通知更新，这里我们选择性更新就行了
                horizontalRecyclerviewAdapter.notifyItemChanged(oldPosition);
                horizontalRecyclerviewAdapter.notifyItemChanged(CurrentPosition);
                //viewpager界面切换
                viewPager.setCurrentItem(position,false);
            }

            @Override
            public void onItemLongClick(View view, int position, List<ImageModel> datas) {
            }
        });



    }

    private void replaceFragment(){
        //创建fragment的工厂类
        FragmentFactory factory=FragmentFactory.getSingleFactoryInstance();
        //创建修改实例
        EmotiomComplateFragment f1= (EmotiomComplateFragment) factory.getFragment(EmotionUtils.EMOTION_CLASSIC_TYPE);
        fragments.add(f1);
        Bundle b;
        for (int i=0;i<7;i++){
            b=new Bundle();
            b.putString("Interge","Fragment-"+i);
            Fragment1 fg= Fragment1.newInstance(Fragment1.class,b);
            fragments.add(fg);
        }

        NoHorizontalScrollerVPAdapter adapter =new NoHorizontalScrollerVPAdapter(getActivity().getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
    }


    /**
     * 是否拦截返回键操作，如果此时表情布局未隐藏，先隐藏表情布局
     * @return true则隐藏表情布局，拦截返回键操作
     *         false 则不拦截返回键操作
     */
    public boolean isInterceptBackPress(){
        return mEmotionKeyboard.interceptBackPress();
    }
}


