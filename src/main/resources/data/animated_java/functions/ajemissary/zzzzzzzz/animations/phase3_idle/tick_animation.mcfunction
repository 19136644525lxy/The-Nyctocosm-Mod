scoreboard players add @s aj.ajemissary.animation.phase3_idle.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajemissary.animation.phase3_idle.local_anim_time
function animated_java:ajemissary/zzzzzzzz/animations/phase3_idle/apply_frame_as_root
execute if score @s aj.ajemissary.animation.phase3_idle.local_anim_time matches 60.. run function animated_java:ajemissary/zzzzzzzz/animations/phase3_idle/end