scoreboard players add @s aj.ajspellplane.animation.fizzle.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajspellplane.animation.fizzle.local_anim_time
function animated_java:ajspellplane/zzzzzzzz/animations/fizzle/apply_frame_as_root
execute if score @s aj.ajspellplane.animation.fizzle.local_anim_time matches 17.. run function animated_java:ajspellplane/zzzzzzzz/animations/fizzle/end