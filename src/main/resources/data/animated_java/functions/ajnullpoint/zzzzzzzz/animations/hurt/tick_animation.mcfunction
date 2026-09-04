scoreboard players add @s aj.ajnullpoint.animation.hurt.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajnullpoint.animation.hurt.local_anim_time
function animated_java:ajnullpoint/zzzzzzzz/animations/hurt/apply_frame_as_root
execute if score @s aj.ajnullpoint.animation.hurt.local_anim_time matches 6.. run function animated_java:ajnullpoint/zzzzzzzz/animations/hurt/end