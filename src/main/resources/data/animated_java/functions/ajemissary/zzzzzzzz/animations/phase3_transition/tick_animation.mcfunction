scoreboard players add @s aj.ajemissary.animation.phase3_transition.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajemissary.animation.phase3_transition.local_anim_time
function animated_java:ajemissary/zzzzzzzz/animations/phase3_transition/apply_frame_as_root
execute if score @s aj.ajemissary.animation.phase3_transition.local_anim_time matches 180.. run function animated_java:ajemissary/zzzzzzzz/animations/phase3_transition/end