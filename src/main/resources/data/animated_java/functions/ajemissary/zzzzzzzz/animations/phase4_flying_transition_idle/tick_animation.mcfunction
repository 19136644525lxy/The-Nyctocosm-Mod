scoreboard players add @s aj.ajemissary.animation.phase4_flying_transition_idle.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajemissary.animation.phase4_flying_transition_idle.local_anim_time
function animated_java:ajemissary/zzzzzzzz/animations/phase4_flying_transition_idle/apply_frame_as_root
execute if score @s aj.ajemissary.animation.phase4_flying_transition_idle.local_anim_time matches 30.. run function animated_java:ajemissary/zzzzzzzz/animations/phase4_flying_transition_idle/end