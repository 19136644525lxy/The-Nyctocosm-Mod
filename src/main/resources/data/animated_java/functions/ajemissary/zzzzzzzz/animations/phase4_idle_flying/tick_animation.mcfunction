scoreboard players add @s aj.ajemissary.animation.phase4_idle_flying.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajemissary.animation.phase4_idle_flying.local_anim_time
function animated_java:ajemissary/zzzzzzzz/animations/phase4_idle_flying/apply_frame_as_root
execute if score @s aj.ajemissary.animation.phase4_idle_flying.local_anim_time matches 50.. run function animated_java:ajemissary/zzzzzzzz/animations/phase4_idle_flying/end