#built using mc-build (https://github.com/mc-build/mc-build)

playsound nyctocosm:tony.disappear master @a[distance=..20] ~ ~ ~
execute as @s[tag=tonymarker] at @s run summon item ~ ~ ~ {Item:{id:"minecraft:written_book",Count:1b,tag:{CustomModelData:4,display:{Name:'[{"translate":"[%s] ","color":"gold","italic":false,"with":[{"translate":"item.nyctocosm.prefix.lore"}]},{"translate":"item.nyctocosm.dreams","color":"aqua","italic":false}]',Lore:['{"translate":"book.byAuthor","color":"gray","italic":false,"with":[{"translate":"item.nyctocosm.dreams.author"}]}','{"translate":"item.nyctocosm.book.generation","color":"gray","italic":false}','{"translate":"item.nyctocosm.dreams.lore","color":"dark_gray"}']},lorebook:1,storyline:2,title:"Dreams of the Dead Star",author:"The Beast",generation:2,pages:['{"translate":"item.nyctocosm.dreams.page1"}'],HideFlags:32b}}}
particle dust 0 0 0 2 ~ ~ ~ 0.6 0.6 0.6 0 500 force
tp @s ~ -100 ~
kill @s[tag=tonymarker]
data merge entity @s[tag=tonycat] {Health:0}