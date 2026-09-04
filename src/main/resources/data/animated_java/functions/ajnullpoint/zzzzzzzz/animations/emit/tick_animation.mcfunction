scoreboard players add @s aj.ajnullpoint.animation.emit.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajnullpoint.animation.emit.local_anim_time
function animated_java:ajnullpoint/zzzzzzzz/animations/emit/apply_frame_as_root
execute if score @s aj.ajnullpoint.animation.emit.local_anim_time matches 40.. run function animated_java:ajnullpoint/zzzzzzzz/animations/emit/end