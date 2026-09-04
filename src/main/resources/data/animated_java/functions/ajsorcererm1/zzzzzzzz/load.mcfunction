scoreboard objectives add aj.i dummy
scoreboard objectives add aj.id dummy
scoreboard objectives add aj.tween_time dummy
scoreboard objectives add aj.anim_time dummy
scoreboard objectives add aj.life_time dummy
scoreboard objectives add aj.ajsorcererm1.export_version dummy
scoreboard objectives add aj.ajsorcererm1.rig_loaded dummy
scoreboard objectives add aj.ajsorcererm1.animation.rise.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm1.animation.idle.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm1.animation.falltransition.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm1.animation.halobeam.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm1.animation.shardhail.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm1.animation.singularitycharge.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm1.animation.frozenblades.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm1.animation.voidmeteor.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm1.animation.rise.loop_mode dummy
scoreboard objectives add aj.ajsorcererm1.animation.idle.loop_mode dummy
scoreboard objectives add aj.ajsorcererm1.animation.falltransition.loop_mode dummy
scoreboard objectives add aj.ajsorcererm1.animation.halobeam.loop_mode dummy
scoreboard objectives add aj.ajsorcererm1.animation.shardhail.loop_mode dummy
scoreboard objectives add aj.ajsorcererm1.animation.singularitycharge.loop_mode dummy
scoreboard objectives add aj.ajsorcererm1.animation.frozenblades.loop_mode dummy
scoreboard objectives add aj.ajsorcererm1.animation.voidmeteor.loop_mode dummy
scoreboard players set $aj.ajsorcererm1.animation.rise aj.id 0
scoreboard players set $aj.ajsorcererm1.animation.idle aj.id 1
scoreboard players set $aj.ajsorcererm1.animation.falltransition aj.id 2
scoreboard players set $aj.ajsorcererm1.animation.halobeam aj.id 3
scoreboard players set $aj.ajsorcererm1.animation.shardhail aj.id 4
scoreboard players set $aj.ajsorcererm1.animation.singularitycharge aj.id 5
scoreboard players set $aj.ajsorcererm1.animation.frozenblades aj.id 6
scoreboard players set $aj.ajsorcererm1.animation.voidmeteor aj.id 7
scoreboard players set $aj.ajsorcererm1.variant.default aj.id 0
scoreboard players set $aj.ajsorcererm1.variant.slash aj.id 1
scoreboard players set $aj.ajsorcererm1.variant.invis aj.id 2
scoreboard players add .aj.last_id aj.id 0
scoreboard players set $aj.loop_mode.loop aj.i 0
scoreboard players set $aj.loop_mode.once aj.i 1
scoreboard players set $aj.loop_mode.hold aj.i 2
scoreboard players set aj.ajsorcererm1.export_version aj.i -1047047815
scoreboard players reset * aj.ajsorcererm1.rig_loaded
execute as @e[type=minecraft:item_display,tag=aj.ajsorcererm1.root] run function animated_java:ajsorcererm1/zzzzzzzz/on_load