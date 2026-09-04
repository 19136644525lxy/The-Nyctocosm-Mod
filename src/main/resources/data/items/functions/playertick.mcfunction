#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[scores={click_bottle=1..},predicate=spells:bottle/holding_bottle] at @s run function items:__generated__/block/13
execute as @s[predicate=items:scythe/holding_scythe,scores={click_scythe=1..}] run function items:__generated__/block/14
execute as @s[predicate=items:wyrmsliver/holding_wyrmsliver,scores={click_sliver=1..}] run function items:__generated__/block/15
scoreboard players remove @s[scores={slashcooldown=1..}] slashcooldown 1
execute as @s[scores={slashcooldown=..0}] run function items:__generated__/block/16
execute as @s[predicate=items:wyrmsliver/holding_wyrmsliver] unless score @s slashcooldown matches 1.. run item modify entity @s weapon.mainhand items:wyrmsliver/slivernotslash
execute as @s[predicate=items:nullfissure/holding_nullfissure,scores={click_fissure=1..}] run function items:__generated__/block/17
scoreboard players remove @s[scores={fissureswingcooldown=1..}] fissureswingcooldown 1
execute as @s[scores={fissureswingcooldown=..0}] run function items:__generated__/block/18
execute as @s[predicate=items:nullfissure/holding_nullfissure] unless score @s fissureswingcooldown matches 1.. run item modify entity @s weapon.mainhand items:nullfissure/fissurenotswing
execute as @s[predicate=items:nullfissure/holding_nullfissure_offhand,scores={click_fissure=1..}] run function items:__generated__/block/19
scoreboard players remove @s[scores={fissurecastcooldown=1..}] fissurecastcooldown 1
execute as @s[scores={fissurecastcooldown=..0}] run function items:__generated__/block/20
execute as @s[predicate=items:semblanceofdoom/holding_semblanceofdoom,scores={click_doom=1..}] run function items:__generated__/block/21
scoreboard players remove @s[scores={doomswingcooldown=1..}] doomswingcooldown 1
execute as @s[scores={doomswingcooldown=..0}] run function items:__generated__/block/22
execute as @s[predicate=items:semblanceofdoom/holding_semblanceofdoom] unless score @s doomswingcooldown matches 1.. run item modify entity @s weapon.mainhand items:semblanceofdoom/doomnotswing
execute as @s[predicate=items:semblanceofdoom/holding_semblanceofdoom_offhand,scores={click_doom=1..}] run function items:__generated__/block/23
scoreboard players remove @s[scores={doomcastcooldown=1..}] doomcastcooldown 1
execute as @s[scores={doomcastcooldown=..0}] run function items:__generated__/block/24
execute as @s[predicate=items:semblanceofdoom/holding_semblanceofdoom_offhand] unless score @s doomcastcooldown matches 1.. run item modify entity @s weapon.offhand items:semblanceofdoom/doomnotswing
execute as @s[nbt={SelectedItem:{id:"minecraft:allay_spawn_egg",Count:1b}}] run function items:scythe/replacehand
scoreboard players reset @s click_scythe
scoreboard players reset @s click_sliver
scoreboard players reset @s click_fissure
scoreboard players reset @s click_doom