scoreboard players add @s aj.ajhypertrace.animation.idle.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajhypertrace.animation.idle.local_anim_time
function zzz_ajhypertrace_internal:animations/idle/apply_frame_as_root
execute if score @s aj.ajhypertrace.animation.idle.local_anim_time matches 20.. run function zzz_ajhypertrace_internal:animations/idle/end