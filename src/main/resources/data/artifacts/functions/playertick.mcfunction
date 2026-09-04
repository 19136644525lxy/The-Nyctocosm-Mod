#built using mc-build (https://github.com/mc-build/mc-build)

tag @s remove a.transposition
execute store success score TempArtif value run clear @s minecraft:red_dye{artifact:1,transposition:1} 0
execute if score TempArtif value matches 1.. run tag @s add a.transposition
tag @s remove a.amelioration
execute store success score TempArtif value run clear @s minecraft:red_dye{artifact:1,amelioration:1} 0
execute if score TempArtif value matches 1.. run tag @s add a.amelioration
tag @s remove a.levitation
execute store success score TempArtif value run clear @s minecraft:red_dye{artifact:1,levitation:1} 0
execute if score TempArtif value matches 1.. run tag @s add a.levitation
tag @s remove a.absorption
execute store success score TempArtif value run clear @s minecraft:red_dye{artifact:1,absorption:1} 0
execute if score TempArtif value matches 1.. run tag @s add a.absorption
tag @s remove a.accumulation
execute store success score TempArtif value run clear @s minecraft:red_dye{artifact:1,accumulation:1} 0
execute if score TempArtif value matches 1.. run tag @s add a.accumulation
tag @s remove a.protection_dream
execute store success score TempArtif value run clear @s minecraft:red_dye{artifact:1,protection_dream:1} 0
execute if score TempArtif value matches 1.. run tag @s add a.protection_dream
tag @s remove a.protection_radiance
execute store success score TempArtif value run clear @s minecraft:red_dye{artifact:1,protection_radiance:1} 0
execute if score TempArtif value matches 1.. run tag @s add a.protection_radiance
tag @s remove a.protection_resonance
execute store success score TempArtif value run clear @s minecraft:red_dye{artifact:1,protection_resonance:1} 0
execute if score TempArtif value matches 1.. run tag @s add a.protection_resonance
tag @s remove a.protection_nightmare
execute store success score TempArtif value run clear @s minecraft:red_dye{artifact:1,protection_nightmare:1} 0
execute if score TempArtif value matches 1.. run tag @s add a.protection_nightmare
tag @s remove a.protection_void
execute store success score TempArtif value run clear @s minecraft:red_dye{artifact:1,protection_void:1} 0
execute if score TempArtif value matches 1.. run tag @s add a.protection_void
tag @s remove a.protection_dissonance
execute store success score TempArtif value run clear @s minecraft:red_dye{artifact:1,protection_dissonance:1} 0
execute if score TempArtif value matches 1.. run tag @s add a.protection_dissonance
tag @s remove a.expulsion
execute store success score TempArtif value run clear @s minecraft:red_dye{artifact:1,expulsion:1} 0
execute if score TempArtif value matches 1.. run tag @s add a.expulsion
tag @s remove a.extraction
execute store success score TempArtif value run clear @s minecraft:red_dye{artifact:1,extraction:1} 0
execute if score TempArtif value matches 1.. run tag @s add a.extraction
execute as @s[tag=a.transposition] at @s run function artifacts:transposition/subtick
execute as @s[tag=a.amelioration] at @s run function artifacts:amelioration/subtick
execute as @s[tag=a.levitation] at @s run function artifacts:levitation/subtick
function artifacts:absorption/subtick
function artifacts:accumulation/subtick
function artifacts:protection/subtick
execute as @s[tag=a.locomotion] at @s run function artifacts:locomotion/subtick
function artifacts:expulsion/subtick
execute as @s run function artifacts:__generated__/block/0